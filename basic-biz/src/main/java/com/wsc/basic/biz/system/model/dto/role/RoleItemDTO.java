package com.wsc.basic.biz.system.model.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色详情
 *
 * @author 吴淑超
 * @date 2021-11-11 14:37
 */
@Data
public class RoleItemDTO {

    @ApiModelProperty(value = "角色id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}
