package com.wsc.basic.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsc.basic.biz.system.model.dto.file.FileItemDTO;
import com.wsc.basic.biz.system.model.entity.SysFile;
import com.wsc.basic.biz.system.model.vo.file.QueryRelationFileVO;
import com.wsc.basic.biz.system.model.vo.file.RelationFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件信息表(SysFile)表服务接口
 *
 * @author 吴淑超
 * @since 2020-07-13 09:10:14
 */
public interface SysFileService extends IService<SysFile> {

    /**
     * 文件上传
     *
     * @param files 文件流数组
     * @return 保存的文件名称
     */
    List<String> upload(MultipartFile[] files);

    /**
     * 上传文件关联业务
     *
     * @param entity 关系实体
     */
    void relation(RelationFileVO entity);

    /**
     * 覆盖方式修改附件关联
     *
     * @param entity 关系实体
     */
    void coverRelation(RelationFileVO entity);

    /**
     * 获取关联业务的实体
     *
     * @param entity 查询条件
     * @return 文件实体
     */
    List<FileItemDTO> getRelationFiles(QueryRelationFileVO entity);

}