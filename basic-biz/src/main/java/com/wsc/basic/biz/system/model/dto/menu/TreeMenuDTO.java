package com.wsc.basic.biz.system.model.dto.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 树形菜单
 *
 * @author 吴淑超
 * @date 2020-06-03 10:03
 */
@Data
public class TreeMenuDTO {

    @ApiModelProperty(value = "菜单id")
    private Long id;

    @JsonIgnore
    @ApiModelProperty(value = "父节点id")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "html页面路径")
    private String path;

    @ApiModelProperty(value = "子菜单")
    private List<TreeMenuDTO> children;

}
