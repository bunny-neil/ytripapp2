package com.ytripapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@SpringBootApplication(exclude = {
    SecurityAutoConfiguration.class,
    ManagementWebSecurityAutoConfiguration.class,
    SessionAutoConfiguration.class,
    RedisAutoConfiguration.class,
    RedisHttpSessionConfiguration.class
})
public class GatewayApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GatewayApplication.class);
    }

    public static void main(String...args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
