package com.wsc.basic.biz.system.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户表分页查询
 *
 * @author 吴淑超
 * @date 2020-06-05 10:08
 */
@Data
public class UserPageDTO {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "角色名称，逗号分隔")
    private String roleName;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}
