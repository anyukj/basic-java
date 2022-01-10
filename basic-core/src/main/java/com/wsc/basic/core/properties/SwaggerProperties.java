package com.wsc.basic.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger文档配置
 *
 * @author 吴淑超
 * @since 2020-09-29 14:57
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.swagger")
public class SwaggerProperties {

    /** 文档分组详情 */
    private List<SwaggerDocketParameter> docket = new ArrayList<>();

}
