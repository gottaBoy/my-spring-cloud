package com.mysting.tomato.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mysting.tomato.common.constant.UaaConstant;

public class TokenUtil {

	
	public static String getClientId() {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		if (user instanceof OAuth2Authentication) {
			OAuth2Authentication athentication = (OAuth2Authentication) user;
			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) athentication.getDetails();
			String clientId = athentication.getOAuth2Request().getClientId();
			return clientId;
		} else {
			return "";
		}
	}
	
    public static String getToken() {
        String token = "";
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

			String header = request.getHeader(UaaConstant.AUTHORIZATION);

			token = StringUtil.isBlank(StringUtil.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ")) ? request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) : StringUtil.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ");

			token = StringUtil.isBlank(request.getHeader(UaaConstant.TOKEN_HEADER)) ? token : request.getHeader(UaaConstant.TOKEN_HEADER);
		} catch (IllegalStateException e) {
		}
        return token;

    }

}
