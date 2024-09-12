package com.me.yaoojbackenduserservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Me.
 * @version 0.0.1
 * @createTime 2024/9/11 16:51
 * @from <a href="https://github.com/Me-60">Me.</a>
 * @blog <a href="https://www.cnblogs.com/Me60">博客园</a>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Yao OJ Service System Cloud UserService API")
                        .version("1.0")
                        .description( "Yao OJ Service System Interface-User")
                );
    }
}
