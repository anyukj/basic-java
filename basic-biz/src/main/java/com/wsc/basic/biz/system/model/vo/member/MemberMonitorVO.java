package com.wsc.basic.biz.system.model.vo.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员监控
 *
 * @author 吴淑超
 * @date 2023-12-17 15:19
 */
@Data
public class MemberMonitorVO {

    @ApiModelProperty(value = "网卡标识")
    private String mac;

    @ApiModelProperty(value = "过期时间")
    private String expirationTime;

    @ApiModelProperty(value = "用户等级：1试用、2普通、3高级")
    private String level;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
