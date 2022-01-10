package com.wsc.basic.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单，不需要登录权限也可以访问的url
 *
 * @author 吴淑超
 * @since 2020-09-29 10:29
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.whitelist")
public class WhitelistProperties {

    /** 白名单列表 */
    private List<String> urls = new ArrayList<>();

}