package com.wsc.basic.biz.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 文件关联类型
 *
 * @author 吴淑超
 * @date 2021-11-11 15:04
 */
@AllArgsConstructor
@NoArgsConstructor
public enum RelationTypeEnum {

    /**
     * 用户附件
     */
    USER(0);

    @Getter
    Integer code;

}
