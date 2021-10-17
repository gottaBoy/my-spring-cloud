package com.mysting.tomato.common.feign;

import com.mysting.tomato.common.util.StringUtil;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.mysting.tomato.common.constant.TraceConstant;
import com.mysting.tomato.common.constant.UaaConstant;
import com.mysting.tomato.common.util.TokenUtil;

import feign.RequestInterceptor;

/**
 * feign拦截器
 */
@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = template -> {
            //传递token
            //使用feign client访问别的微服务时，将accessToken header
            if (StringUtil.isNotBlank(TokenUtil.getToken())) {
            	//弱认证
            	//template.header(UaaConstant.TOKEN_HEADER, TokenUtil.getToken());
            	//强认证
            	template.header(UaaConstant.AUTHORIZATION,  OAuth2AccessToken.BEARER_TYPE  +  " "  +  TokenUtil.getToken() );
            }
            //传递traceId
            String traceId = StringUtil.isNotBlank(MDC.get(TraceConstant.LOG_TRACE_ID)) ? MDC.get(TraceConstant.LOG_TRACE_ID) : MDC.get(TraceConstant.LOG_B3_TRACEID);
            if (StringUtil.isNotBlank(traceId)) {
                template.header(TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
            }


        };

        return requestInterceptor;
    }
}
