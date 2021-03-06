package com.mysting.tomato.uaa.feign;

import java.util.Map;

import com.mysting.tomato.uaa.feign.fallback.UserFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysting.tomato.common.auth.details.LoginAppUser;
import com.mysting.tomato.common.feign.FeignExceptionConfig;
import com.mysting.tomato.common.model.SysUser;
import com.mysting.tomato.common.web.PageResult;

/**
* @author 作者 owen 
* @version 创建时间：2017年11月12日 上午22:57:51
* 调用用户中心中的userdetail对象，用户oauth中的登录
* 获取的用户与页面输入的密码 进行BCryptPasswordEncoder匹配
 */

@FeignClient(value="user-center",configuration = FeignExceptionConfig.class ,fallbackFactory = UserFeignClientFallbackFactory.class, decode404 = true)
public interface UserFeignClient {

	/**
	 * feign rpc访问远程/users-anon/login接口
	 * @param username
	 * @return
	 */
    @GetMapping(value = "/users-anon/login", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);


	@GetMapping(value = "/users-anon/mobile", params = "mobile")
	LoginAppUser findByMobile(@RequestParam("mobile") String mobile);

	
	@GetMapping(value = "/users", params = "params")
	PageResult<SysUser> findUsers(@RequestParam  Map<String, Object> params);
    
}
