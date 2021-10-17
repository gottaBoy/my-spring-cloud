package com.mysting.tomato.common.config;

import com.mysting.tomato.common.filter.TraceContextFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@SuppressWarnings("all") 
@ConditionalOnClass(WebMvcConfigurer.class)
public class TraceFilterConfig {
	
	@Bean
    public FilterRegistrationBean requestContextRepositoryFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new TraceContextFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
