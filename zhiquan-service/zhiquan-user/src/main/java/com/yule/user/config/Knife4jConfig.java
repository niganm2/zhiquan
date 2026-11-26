package com.yule.user.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 接口文档配置（Knife4j / OpenAPI3）
 * 文档地址：http://localhost:51801/doc.html
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI zhiquanUserOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("zhiquan-user 用户服务 API")
                .description("APP 用户登录相关接口")
                .version("1.0.0"));
    }
}
