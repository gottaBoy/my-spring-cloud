package com.mysting.tomato.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author 作者 owen
 * @version 创建时间：2017年11月12日 上午22:57:51 类说明
 * @EnableOAuth2Sso注解。如果WebSecurityConfigurerAdapter类上注释了@EnableOAuth2Sso注解， 那么将会添加身份验证过滤器和身份验证入口。
 *                                                                           如果只有一个@EnableOAuth2Sso注解没有编写在WebSecurityConfigurerAdapter上，
 *                                                                           那么它将会为所有路径启用安全，并且会在基于HTTP
 *                                                                           Basic认证的安全链之前被添加。详见@EnableOAuth2Sso的注释。
 */
@EnableWebSecurity
public class SSOSecurityConfig extends WebSecurityConfigurerAdapter {

	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
		  http.authorizeRequests(req -> req
	                .antMatchers("/","/webjars/**", "/login**")
	                .permitAll().anyRequest().authenticated())
	            .oauth2Login(Customizer.withDefaults());
	    }

	    @Bean
	    WebClient webClient(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository) {
	        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, authorizedClientRepository);
	        oauth2.setDefaultOAuth2AuthorizedClient(true);
	        return WebClient.builder()
	            .apply(oauth2.oauth2Configuration())
	            .build();
	    }

}
