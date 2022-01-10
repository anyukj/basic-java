package com.wsc.basic.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 吴淑超
 * @since 2020-10-09 16:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDO<T extends Model<?>> extends Model<T> {

    @ApiModelProperty(value = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人id")
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * 整数类型下 newVersion = oldVersion + 1
     * newVersion 会回写到 entity 中
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     */
    @ApiModelProperty(value = "乐观锁版本号")
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Long version;

}
