package com.wsc.basic.biz.system.service.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsc.basic.biz.system.dao.SysFileMapper;
import com.wsc.basic.biz.system.model.dto.file.FileItemDTO;
import com.wsc.basic.biz.system.model.entity.SysFile;
import com.wsc.basic.biz.system.model.vo.file.QueryRelationFileVO;
import com.wsc.basic.biz.system.model.vo.file.RelationFileVO;
import com.wsc.basic.biz.system.service.SysFileService;
import com.wsc.basic.core.config.security.UserContext;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.exception.GlobalException;
import com.wsc.basic.core.model.TokenEntity;
import com.wsc.basic.core.properties.FileProperties;
import com.wsc.basic.core.utils.SuperBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件信息表(SysFile)表服务实现类
 *
 * @author 吴淑超
 * @since 2020-07-13 09:10:14
 */
@Slf4j
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Resource
    private FileProperties fileProperties;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> upload(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw new GlobalException(I18nMsgConstants.NO_FILE);
        }

        TokenEntity tokenEntity = UserContext.getUser();
        List<String> result = new ArrayList<>();
        for (MultipartFile file : files) {
            SysFile sysFile = new SysFile();
            sysFile.setOriginalName(file.getOriginalFilename());
            sysFile.setFileType(file.getContentType());
            sysFile.setNewName(StrUtil.format("{}.{}",
                    IdUtil.fastSimpleUUID(),
                    FileNameUtil.extName(file.getOriginalFilename())));
            if (tokenEntity != null) {
                sysFile.setCreateUser(tokenEntity.getUserId());
            }

            if (sysFile.getFileType().indexOf("image/") == 0) {
                // 图片类型的生成缩略图
                try {
                    BufferedImage image = ImgUtil.read(file.getInputStream());
                    if (image != null) {
                        double vw = NumberUtil.div(fileProperties.getThumbWidth(), image.getWidth());
                        double vh = NumberUtil.div(fileProperties.getThumbHeight(), image.getHeight());
                        Image scaleImage = ImgUtil.scale(image, (float) NumberUtil.min(vw, vh));
                        String base64Image = ImgUtil.toBase64(scaleImage, "png");
                        sysFile.setThumb(StrUtil.addPrefixIfNot(base64Image, "data:image/png;base64,"));
                    }
                } catch (Exception e) {
                    log.error("生成缩略图失败：{}", e.getMessage());
                }
            }
            // 保存文件到本地
            try {
                FileWriter writer = new FileWriter(StrUtil.format("{}/{}", fileProperties.getUploadDir(), sysFile.getNewName()));
                writer.write(file.getBytes(), 0, Math.toIntExact(file.getSize()));
            } catch (IOException e) {
                throw new GlobalException(StrUtil.format("保存文件失败：{}", e.getMessage()));
            }
            // 保存文件到数据库
            boolean saveFlag = super.save(sysFile);
            if (!saveFlag) {
                throw new GlobalException(StrUtil.format("保存失败：{}", sysFile.getOriginalName()));
            }
            // 返回内容
            result.add(sysFile.getNewName());
        }
        return result;
    }

    @Override
    public void relation(RelationFileVO entity) {
        if (entity.getFileNames() != null && !entity.getFileNames().isEmpty()) {
            super.update(Wrappers.lambdaUpdate(SysFile.class)
                    .in(SysFile::getNewName, entity.getFileNames())
                    .set(SysFile::getRelationId, entity.getRelationId())
                    .set(SysFile::getRelationType, entity.getRelationType())
                    .set(SysFile::getRelationChildType, entity.getRelationChildType())
            );
        }
    }

    @Override
    public void coverRelation(RelationFileVO entity) {
        // 把关联数据清空
        LambdaUpdateWrapper<SysFile> updateWrapper = Wrappers.lambdaUpdate(SysFile.class)
                .set(SysFile::getRelationId, null)
                .set(SysFile::getRelationType, null)
                .set(SysFile::getRelationChildType, null)
                .eq(SysFile::getRelationId, entity.getRelationId())
                .eq(SysFile::getRelationType, entity.getRelationType());
        if (entity.getRelationChildType() != null) {
            updateWrapper.eq(SysFile::getRelationChildType, entity.getRelationChildType());
        }
        super.update(updateWrapper);
        // 关联文件数据
        relation(entity);
    }

    @Override
    public List<FileItemDTO> getRelationFiles(QueryRelationFileVO entity) {
        LambdaQueryWrapper<SysFile> queryWrapper = Wrappers.lambdaQuery(SysFile.class)
                .eq(SysFile::getRelationId, entity.getRelationId())
                .eq(SysFile::getRelationType, entity.getRelationType());
        if (entity.getRelationChildType() != null) {
            queryWrapper.eq(SysFile::getRelationChildType, entity.getRelationChildType());
        }
        List<SysFile> sysFiles = super.list(queryWrapper);
        return SuperBeanUtils.copyListProperties(sysFiles, FileItemDTO::new);
    }

}