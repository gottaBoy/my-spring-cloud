package com.mysting.tomato.uaa.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 开启session共享
 */
@EnableRedisHttpSession
public class SessionConfig {
	@Bean
	public CookieSerializer httpSessionIdResolver() {
		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		// 取消仅限同一站点设置
		cookieSerializer.setSameSite(null);
		return cookieSerializer;
	}
}
