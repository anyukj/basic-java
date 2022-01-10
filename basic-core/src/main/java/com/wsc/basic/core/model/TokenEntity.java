package com.wsc.basic.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * JwtToken实体
 *
 * @author 吴淑超
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity implements Serializable {

    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "角色列表,逗号分隔多个角色")
    private String roleIds;

}
