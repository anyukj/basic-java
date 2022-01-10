package com.wsc.basic.biz.system.model.dto.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字典详情
 *
 * @author 吴淑超
 * @date 2021-11-11 14:37
 */
@Data
public class DictionaryItemDTO {

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

}
