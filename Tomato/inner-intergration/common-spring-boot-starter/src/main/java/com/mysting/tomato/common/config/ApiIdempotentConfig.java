package com.mysting.tomato.common.config;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mysting.tomato.common.interceptor.AccessLimitInterceptor;
import com.mysting.tomato.common.interceptor.ApiIdempotentInterceptor;
import com.mysting.tomato.redis.util.RedisUtil;

@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class ApiIdempotentConfig implements WebMvcConfigurer {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessLimitInterceptor(redisUtil));
        registry.addInterceptor(new ApiIdempotentInterceptor(redisTemplate)).addPathPatterns("/**");


    }
}
