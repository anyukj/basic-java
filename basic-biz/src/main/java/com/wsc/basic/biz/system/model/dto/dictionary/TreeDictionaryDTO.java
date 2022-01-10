package com.wsc.basic.biz.system.model.dto.dictionary;

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
public class TreeDictionaryDTO {

    @ApiModelProperty(value = "字典id")
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

    @ApiModelProperty(value = "子菜单")
    private List<TreeDictionaryDTO> children;

}
