package com.wsc.basic.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsc.basic.core.annotation.LockPoint;
import com.wsc.basic.core.properties.FileProperties;
import com.wsc.basic.biz.system.dao.SysFileMapper;
import com.wsc.basic.biz.system.service.SysFileService;
import com.wsc.basic.biz.system.model.dto.file.FileItemDTO;
import com.wsc.basic.biz.system.model.entity.SysFile;
import com.wsc.basic.biz.system.model.vo.file.QueryRelationFileVO;
import com.wsc.basic.biz.system.model.vo.file.RelationFileVO;
import com.wsc.basic.core.config.security.UserContext;
import com.wsc.basic.core.constant.I18nMsgConstants;
import com.wsc.basic.core.exception.GlobalException;
import com.wsc.basic.core.model.TokenEntity;
import com.wsc.basic.core.utils.SuperBeanUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
            String key = UUID.randomUUID().toString().replace("-", "");
            String prefix = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(Objects.requireNonNull(file.getOriginalFilename())
                            .lastIndexOf(".") + 1);

            SysFile sysFile = new SysFile();
            sysFile.setOriginalName(file.getOriginalFilename());
            sysFile.setFileType(file.getContentType());
            sysFile.setNewName(String.format("%s.%s", key, prefix));
            if (tokenEntity != null) {
                sysFile.setCreateUser(tokenEntity.getUserId());
            }
            try {
                // 图片类型的生成缩略图
                if (sysFile.getFileType().indexOf("image/") == 0) {
                    BufferedImage bufferedImage = Thumbnails.of(file.getInputStream())
                            .size(fileProperties.getThumbWidth(), fileProperties.getThumbHeight())
                            .asBufferedImage();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, prefix, bos);
                    String imageBase64 = Base64.encodeBase64String(bos.toByteArray())
                            .replace("\r", "")
                            .replace("\n", "");
                    sysFile.setThumb(String.format("data:image/png;base64,%s", imageBase64));
                }
            } catch (IOException e) {
                throw new GlobalException(String.format("生成缩略图失败：%s", e.getMessage()));
            }

            // 保存文件到本地
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s/%s", fileProperties.getUploadDir(), sysFile.getNewName()));
                FileCopyUtils.copy(file.getBytes(), fileOutputStream);
            } catch (IOException e) {
                throw new GlobalException(String.format("保存文件失败：%s", e.getMessage()));
            }
            // 保存文件到数据库
            boolean saveFlag = super.save(sysFile);
            if (!saveFlag) {
                throw new GlobalException(String.format("保存失败：%s", sysFile.getOriginalName()));
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