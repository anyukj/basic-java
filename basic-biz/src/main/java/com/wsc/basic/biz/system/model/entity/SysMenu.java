package com.wsc.basic.biz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsc.basic.core.model.BaseDeletionDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能菜单表
 *
 * @author 吴淑超
 * @since 2020-06-03 09:59:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseDeletionDO<SysMenu> {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父节点id：0表示一级节点")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "html页面路径")
    private String path;

    @ApiModelProperty(value = "排序序号")
    private Integer sort;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}