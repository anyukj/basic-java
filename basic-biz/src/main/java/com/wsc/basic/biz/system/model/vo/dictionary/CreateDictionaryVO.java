package com.wsc.basic.biz.system.model.vo.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 通用字典创建实体
 *
 * @author 吴淑超
 * @since 2020-07-09 10:58:36
 */
@Data
public class CreateDictionaryVO {

    @ApiModelProperty(value = "父节点id：0表示一级节点")
    private Long parentId;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @NotBlank(message = "代码不能为空")
    @ApiModelProperty(value = "代码", required = true)
    private String code;

    @NotBlank(message = "值不能为空")
    @ApiModelProperty(value = "值", required = true)
    private String value;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}