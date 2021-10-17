package com.mysting.tomato.client.config;

import org.springframework.context.annotation.Configuration;

import com.didispace.swagger.butler.EnableSwaggerButler;

/**
 * swagger 聚合文档配置
 * zuul routers 映射具体服务的/v2/api-docs swagger 
 */
@Configuration
@EnableSwaggerButler
public class SwaggerConfig {

}
