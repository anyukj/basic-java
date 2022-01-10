package com.wsc.basic.biz.system.model.vo.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用字典查询
 *
 * @author 吴淑超
 * @since 2020-07-09 10:58:36
 */
@Data
public class QueryDictionaryVO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

    @ApiModelProperty(value = "true只返回有父级节点的数据，false返回树形结构数据")
    private Boolean returnParent;

}