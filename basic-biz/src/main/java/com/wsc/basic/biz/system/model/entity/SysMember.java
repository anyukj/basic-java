package com.wsc.basic.biz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsc.basic.core.model.BaseDeletionDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 会员表
 *
 * @author 吴淑超
 * @since 2020-06-01 17:40:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMember extends BaseDeletionDO<SysMember> {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "卡号")
    private String card;

    @ApiModelProperty(value = "网卡标识")
    private String mac;

    @ApiModelProperty(value = "用户备注")
    private String remark;

    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}