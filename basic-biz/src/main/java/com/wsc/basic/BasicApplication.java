package com.wsc.basic;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 吴淑超
 * @since 2020-09-29 11:08
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

}
