package com.wsc.basic.biz.system.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 修改用户密码
 *
 * @author 吴淑超
 * @date 2020-02-15 10:44
 */
@Data
public class UpdatePasswordVO {

    @NotBlank(message = "旧用户密码为空")
    @Length(min = 6, max = 32, message = "用户旧密码长度为6 - 32")
    @ApiModelProperty(value = "旧用户密码", required = true)
    private String oldPassword;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "用户新密码长度为6 - 32")
    @ApiModelProperty(value = "新用户密码", required = true)
    private String newPassword;

}
