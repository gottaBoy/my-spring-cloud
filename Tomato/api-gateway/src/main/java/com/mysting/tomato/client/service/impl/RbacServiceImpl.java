package com.mysting.tomato.client.service.impl;
/**
 * 
 */

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.mysting.tomato.client.service.SysClientService;
import com.mysting.tomato.uaa.client.service.RbacService;

/**
 * API 级别权限认证
 * 网关实现应用服务API接口
 */

@Service("rbacService")
@SuppressWarnings("all")
public class RbacServiceImpl implements RbacService {

	@Autowired
	private SysClientService sysClientService;
	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**
	 * @param request HttpServletRequest
	 * @param authentication 认证信息
	 * @return 是否有权限
	 */
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		boolean hasPermission = false;
		if (user != null) {
			if (user instanceof OAuth2Authentication) {
				OAuth2Authentication athentication = (OAuth2Authentication) user;
				String clientId = athentication.getOAuth2Request().getClientId();
				Map map = sysClientService.getClient(clientId);
				if (map == null) {
					hasPermission = false ;
				} else {
					List<Map> list = sysClientService.listByClientId(Long.valueOf(String.valueOf(map.get("id"))));
					hasPermission = list.stream().anyMatch(item -> antPathMatcher.match(String.valueOf(item.get("path")), request.getRequestURI()));
				}
			}
		}
		return hasPermission;
	}

}
