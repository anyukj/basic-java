package com.wsc.basic.biz.system.model.vo.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 通用字典修改实体
 *
 * @author 吴淑超
 * @since 2020-07-09 10:58:36
 */
@Data
public class UpdateDictionaryVO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Long id;

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

    @Range(max = 1, message = "状态只能为0 - 1")
    @ApiModelProperty(value = "状态：0正常、1停用", required = true)
    private Integer status;

}