package com.wsc.basic.biz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsc.basic.core.model.BaseDeletionDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件信息表实体
 *
 * @author 吴淑超
 * @since 2020-07-13 09:10:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFile extends BaseDeletionDO<SysFile> {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文件原名称")
    private String originalName;

    @ApiModelProperty(value = "文件新名称（uuid随机）")
    private String newName;

    @ApiModelProperty(value = "文件类型（识别是否是图片或文档...）")
    private String fileType;

    @ApiModelProperty(value = "缩略图BASE64")
    private String thumb;

    @ApiModelProperty(value = "关联id")
    private Long relationId;

    @ApiModelProperty(value = "关联的业务类型（使用枚举）")
    private Integer relationType;

    @ApiModelProperty(value = "关联业务子类型（一个类型下存在多种业务的时候使用）")
    private Integer relationChildType;

}