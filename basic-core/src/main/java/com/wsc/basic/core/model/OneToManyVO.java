package com.wsc.basic.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 一对多关联实体
 *
 * @author 吴淑超
 */
@Data
public class OneToManyVO {

    @NotNull(message = "one不能为空")
    @ApiModelProperty(value = "根据实际情况使用，一般为id", required = true)
    private Long one;

    @ApiModelProperty(value = "根据实际情况使用，多的一方的id列表")
    private List<Long> many;

}
