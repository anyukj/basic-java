package com.wsc.basic.biz.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsc.basic.core.model.BaseDeletionDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * API资源表
 *
 * @author 吴淑超
 * @since 2020-06-02 10:05:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysResource extends BaseDeletionDO<SysResource> {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "详细描述说明")
    private String description;

    @ApiModelProperty(value = "资源路径")
    private String url;

    @ApiModelProperty(value = "请求方法(GET查询、POST新增、PUT修改 、DELETE删除)")
    private String method;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}