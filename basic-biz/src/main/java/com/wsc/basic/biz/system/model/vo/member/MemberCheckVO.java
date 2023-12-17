package com.wsc.basic.biz.system.model.vo.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 会员校验
 *
 * @author 吴淑超
 * @date 2023-12-17 15:19
 */
@Data
public class MemberCheckVO {

    @NotBlank(message = "卡号不能为空")
    @ApiModelProperty(value = "卡号")
    private String card;

    @NotBlank(message = "网卡标识不能为空")
    @ApiModelProperty(value = "网卡标识")
    private String mac;

}
