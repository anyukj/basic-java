package com.wsc.basic.biz.system.model.vo.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改菜单
 *
 * @author 吴淑超
 * @date 2020-06-08 9:17
 */
@Data
public class UpdateMenuVO {

    @NotNull(message = "菜单id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "父节点id：0表示一级节点")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称", required = true)
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @NotBlank(message = "页面路径不能为空")
    @ApiModelProperty(value = "html页面路径", required = true)
    private String path;

    @ApiModelProperty(value = "排序序号")
    private Integer sort;

    @Range(max = 1, message = "状态只能为0 - 1")
    @ApiModelProperty(value = "状态：0正常、1停用", required = true)
    private Integer status;

}
