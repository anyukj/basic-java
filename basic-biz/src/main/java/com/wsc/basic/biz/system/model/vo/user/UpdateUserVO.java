package com.wsc.basic.biz.system.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户表修改实体
 *
 * @author 吴淑超
 * @date 2020-06-08 9:17
 */
@Data
public class UpdateUserVO {

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String fullName;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "文件列表")
    private List<String> files;

    @Range(max = 1, message = "状态只能为0 - 1")
    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}
