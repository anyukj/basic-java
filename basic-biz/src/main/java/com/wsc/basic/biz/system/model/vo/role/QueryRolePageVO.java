package com.wsc.basic.biz.system.model.vo.role;

import com.wsc.basic.core.model.BasePageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表分页查询
 *
 * @author 吴淑超
 * @date 2020-06-16 9:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRolePageVO extends BasePageVO {

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "状态：0正常、1停用、9删除")
    private Integer status;

}
