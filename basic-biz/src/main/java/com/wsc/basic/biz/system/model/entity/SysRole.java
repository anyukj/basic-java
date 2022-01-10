package com.wsc.basic.biz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsc.basic.core.model.BaseDeletionDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 *
 * @author 吴淑超
 * @since 2020-06-03 11:04:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseDeletionDO<SysRole> {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}