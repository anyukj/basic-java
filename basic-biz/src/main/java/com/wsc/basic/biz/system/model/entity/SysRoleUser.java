package com.wsc.basic.biz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色用户关联表实体
 *
 * @author 吴淑超
 * @since 2020-06-23 16:39:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleUser extends Model<SysRoleUser> {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

}