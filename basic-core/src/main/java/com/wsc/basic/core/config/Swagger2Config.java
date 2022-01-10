package com.wsc.basic.core.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.wsc.basic.core.properties.SwaggerProperties;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 访问文档地址：http://localhost:8080/doc.html
 *
 * @author 吴淑超
 * @date 2020-05-20 10:55
 */
@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {

    public Swagger2Config(DefaultListableBeanFactory beanFactory, SwaggerProperties properties, OpenApiExtensionResolver openApiExtensionResolver) {
        // header加入Authorization参数
        Parameter parameter = new ParameterBuilder()
                .name("Authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        // 通过参数注册文档
        Optional.ofNullable(properties.getDocket())
                .ifPresent(item -> {
                    // 遍历docker文档参数
                    item.forEach(docketParameter -> {
                        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                                .groupName(docketParameter.getGroupName())
                                .apiInfo(apiInfo())
                                .select()
                                .apis(basePackage(docketParameter.getBasePackage()))
                                .build()
                                .extensions(openApiExtensionResolver.buildExtensions(docketParameter.getGroupName()))
                                .globalOperationParameters(Collections.singletonList(parameter));
                        // 注册bean
                        beanFactory.registerSingleton(docket.toString(), docket);
                    });
                });
    }


    /**
     * Api文档基本信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基础平台")
                .description("基础平台框架")
                .version("1.0")
                .build();
    }

    private Predicate<RequestHandler> basePackage(String basePackage) {
        return input -> Optional.ofNullable(input.declaringClass())
                .map(handlerPackage(basePackage))
                .orElse(true);
    }

    private Function<Class<?>, Boolean> handlerPackage(String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(";")) {
                boolean isMatch = input.getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

}
