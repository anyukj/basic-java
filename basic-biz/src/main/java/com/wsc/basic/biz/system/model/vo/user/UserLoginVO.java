package com.wsc.basic.biz.system.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录
 *
 * @author 吴淑超
 * @date 2020-02-14 17:17
 */
@Data
public class UserLoginVO {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "请输入密码长度为6 - 32")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
