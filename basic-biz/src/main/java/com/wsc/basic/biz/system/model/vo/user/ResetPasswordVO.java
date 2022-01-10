package com.wsc.basic.biz.system.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 重置用户密码
 *
 * @author 吴淑超
 * @date 2020-02-15 10:44
 */
@Data
public class ResetPasswordVO {

    @NotNull(message = "用户id列表为空")
    @Size(min = 1, message = "用户id列表不能为空")
    @ApiModelProperty(value = "用户id列表", required = true)
    private List<Integer> ids;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "用户密码长度为6 - 32")
    @ApiModelProperty(value = "新密码", required = true)
    private String password;

}
