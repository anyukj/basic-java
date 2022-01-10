package com.wsc.basic.biz.system.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 用户表创建实体
 *
 * @author 吴淑超
 * @date 2020-06-08 9:17
 */
@Data
public class CreateUserVO {

    @NotBlank(message = "登录名不能为空")
    @ApiModelProperty(value = "登录名", required = true)
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "用户新密码长度为6 - 32")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String fullName;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "文件列表")
    private List<String> files;

}
