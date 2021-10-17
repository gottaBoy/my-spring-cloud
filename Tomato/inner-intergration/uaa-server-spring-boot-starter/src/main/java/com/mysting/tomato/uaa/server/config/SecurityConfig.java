package com.mysting.tomato.uaa.server.config;

import com.mysting.tomato.uaa.server.constant.SecurityConstant;
import com.mysting.tomato.uaa.server.handle.OauthLogoutHandler;
import com.mysting.tomato.uaa.server.provider.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mysting.tomato.common.auth.props.PermitUrlProperties;

/**
 * spring security配置
 * 在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(PermitUrlProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired(required = false)
	private AuthenticationEntryPoint authenticationEntryPoint; // 自定义异常处理端口 默认空
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OauthLogoutHandler oauthLogoutHandler;
	@Autowired
	private PermitUrlProperties permitUrlProperties ;
	 
	@Autowired
	private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**", "/doc.html", "/login.html");
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/health");
		// 忽略登录界面
		web.ignoring().antMatchers("/login.html");
		web.ignoring().antMatchers("/oauth/user/token");
		web.ignoring().antMatchers("/oauth/client/token");
		web.ignoring().antMatchers("/validata/code/**");
		web.ignoring().antMatchers("/sms/**");
		web.ignoring().antMatchers("/authentication/**");
		web.ignoring().antMatchers(permitUrlProperties.getIgnored());
		
	}
	/**
	 * 认证管理
	 * 
	 * @return 认证管理对象
	 * @throws Exception
	 *             认证异常信息
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.authorizeRequests()
				.anyRequest().authenticated();
		http.formLogin().loginPage(SecurityConstant.LOGIN_PAGE).loginProcessingUrl(SecurityConstant.LOGIN_PROCESSING_URL)
				.successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler);

		// 基于密码 等模式可以无session,不支持授权码模式
		if (authenticationEntryPoint != null) {
			http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		} else {
			// 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		}

		///默认退出/logout
		http.logout()
				.logoutSuccessHandler(oauthLogoutHandler)
				.addLogoutHandler(oauthLogoutHandler)
				// 无效会话
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                // 清除身份验证
				.logoutSuccessUrl(SecurityConstant.LOGIN_PAGE);

		//注册到AuthenticationManager中去 增加支持SmsCodeAuthenticationToken
		http.authenticationProvider(smsCodeAuthenticationProvider);

		// http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
		// 解决不允许显示在iframe的问题
		http.headers().frameOptions().disable();
		http.headers().cacheControl();

	}

	/**
	 * 全局用户信息
	 * 
	 * @param auth
	 *            认证管理
	 * @throws Exception
	 *             用户认证异常信息
	 */
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider()) ;
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		 DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		 daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider ;
	}
	
}
