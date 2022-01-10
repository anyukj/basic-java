package com.wsc.basic.core.properties;

import lombok.Data;

/**
 * Swagger文档配置参数
 *
 * @author 吴淑超
 * @since 2020-09-29 14:57
 */
@Data
public class SwaggerDocketParameter {

    /** 分组名称 */
    private String groupName;

    /** 扫描的包路径，多个使用 ; 分割 */
    private String basePackage;

}
