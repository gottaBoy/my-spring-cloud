package com.mysting.tomato.client.token;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mysting.tomato.client.service.SysClientService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * @author 作者 owen 
 * @version 创建时间：2018年2月1日 下午9:47:00 类说明
 */
@Component
@SuppressWarnings("all")
public class AuthorizeConfigManager implements ReactiveAuthorizationManager<AuthorizationContext> {

	@Resource
	private SysClientService sysClientService ;

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
			AuthorizationContext authorizationContext) {
		return authentication.map(auth -> {

			// TODO 目前都是true
			boolean hasPermission = false;

			ServerWebExchange exchange = authorizationContext.getExchange();
			ServerHttpRequest request = exchange.getRequest();

			if (auth instanceof OAuth2Authentication) {

				OAuth2Authentication athentication = (OAuth2Authentication) auth;

				String clientId = athentication.getOAuth2Request().getClientId();

				Map map = sysClientService.getClient(clientId);

				if (map == null) {
					return new AuthorizationDecision(false);
				} else {
					List<Map> list = sysClientService.listByClientId(Long.valueOf(String.valueOf(map.get("id"))));

					boolean flag = list.stream().anyMatch(item -> antPathMatcher.match(String.valueOf(item.get("path")),request.getURI().getPath()));
					
					return new AuthorizationDecision(flag);
				}

			}

			// boolean isPermission = super.hasPermission(auth,
			// request.getMethodValue(), request.getURI().getPath());

			return new AuthorizationDecision(hasPermission);
		}).defaultIfEmpty(new AuthorizationDecision(false));
	}

}
