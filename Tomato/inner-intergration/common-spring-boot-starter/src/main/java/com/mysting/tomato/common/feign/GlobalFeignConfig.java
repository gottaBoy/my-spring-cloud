package com.mysting.tomato.common.feign;

import org.springframework.context.annotation.Bean;

import feign.Logger.Level;

public class GlobalFeignConfig {

    @Bean
    public Level level() {
        return Level.FULL;
    }
}
