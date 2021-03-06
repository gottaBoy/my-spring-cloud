package com.mysting.tomato.uaa.client;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysting.tomato.common.auth.props.PermitUrlProperties;
import com.mysting.tomato.common.feign.FeignInterceptorConfig;
import com.mysting.tomato.common.feign.GlobalFeignConfig;
import com.mysting.tomato.common.rest.RestTemplateConfig;
import com.mysting.tomato.uaa.client.authorize.AuthorizeConfigManager;

@Component
@Configuration
@EnableResourceServer
@SuppressWarnings("all") 
@AutoConfigureAfter(TokenStore.class)
@EnableConfigurationProperties(PermitUrlProperties.class)
@Import({RestTemplateConfig.class,FeignInterceptorConfig.class})
@EnableFeignClients(defaultConfiguration= GlobalFeignConfig.class)
//??????spring security ??????
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UAAClientAutoConfig extends ResourceServerConfigurerAdapter {

	// ??????oauth_client_details??? resource_ids?????? ?????????????????????
	// client_id??????????????????resource_ids??????????????????
	private static final String DEMO_RESOURCE_ID = "";

	@Resource
	private ObjectMapper objectMapper; // springmvc?????????????????????json?????????


	@Autowired(required = false)
	private TokenStore tokenStore;
 

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;

	@Autowired
	private OAuth2WebSecurityExpressionHandler expressionHandler;
	@Autowired
	private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

	@Autowired
	private PermitUrlProperties permitUrlProperties;

	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(permitUrlProperties.getIgnored());
	}
	

	
	 

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

		if (tokenStore != null) {
			resources.tokenStore(tokenStore);
		}  
		resources.stateless(true);
		// ??????????????????????????? 
		resources.authenticationEntryPoint(authenticationEntryPoint);
		resources.expressionHandler(expressionHandler);
		resources.accessDeniedHandler(oAuth2AccessDeniedHandler);

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.headers().frameOptions().disable();

		authorizeConfigManager.config(http.authorizeRequests());

	}

}
