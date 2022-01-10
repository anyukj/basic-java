package com.wsc.basic.biz.system.model.vo.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建菜单
 *
 * @author 吴淑超
 * @date 2020-06-08 9:17
 */
@Data
public class CreateMenuVO {

    @ApiModelProperty(value = "父节点id：0表示一级节点")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称", required = true)
    private String name;

    @NotBlank(message = "页面路径不能为空")
    @ApiModelProperty(value = "html页面路径", required = true)
    private String path;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "排序序号")
    private Integer sort;

}
