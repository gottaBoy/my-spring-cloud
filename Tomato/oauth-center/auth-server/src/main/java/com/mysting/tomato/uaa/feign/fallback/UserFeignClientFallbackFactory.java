package com.mysting.tomato.uaa.feign.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.mysting.tomato.common.auth.details.LoginAppUser;
import com.mysting.tomato.common.model.SysUser;
import com.mysting.tomato.common.web.PageResult;
import com.mysting.tomato.uaa.feign.UserFeignClient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
	@Override
	public UserFeignClient create(Throwable throwable) {
		return new UserFeignClient() {

			@Override
			public LoginAppUser findByUsername(String username) {
				log.error("通过用户名查询用户异常:{}", username, throwable);
				return new LoginAppUser() ;
			}

			@Override
			public LoginAppUser findByMobile(String mobile) {
				log.error("通过手机号查询用户异常:{}", mobile, throwable);
				return new LoginAppUser();
			}

			@Override
			public PageResult<SysUser> findUsers(Map<String, Object> params) {
				log.error("查询用户列表异常:{}");
				return null;
			}

		};
	}
}
