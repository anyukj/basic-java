package com.wsc.basic.biz.system.model.vo.user;

import com.wsc.basic.core.model.BasePageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表分页查询
 *
 * @author 吴淑超
 * @date 2020-06-05 10:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryUserPageVO extends BasePageVO {

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}
