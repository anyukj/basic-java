package com.wsc.basic.biz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsc.basic.core.model.BaseDeletionDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用字典实体
 *
 * @author 吴淑超
 * @since 2020-07-09 10:58:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictionary extends BaseDeletionDO<SysDictionary> {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父节点id")
    private Long parentId;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}