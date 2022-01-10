package com.wsc.basic.biz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsc.basic.core.model.BaseDeletionDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 *
 * @author 吴淑超
 * @since 2020-06-01 17:40:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseDeletionDO<SysUser> {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}