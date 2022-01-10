package com.wsc.basic.core.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 吴淑超
 * @since 2020-10-09 16:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDeletionDO<T extends BaseDO<?>> extends BaseDO<T> {

    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty("逻辑删除状态：0正常、1删除")
    private Integer deletion;

}
