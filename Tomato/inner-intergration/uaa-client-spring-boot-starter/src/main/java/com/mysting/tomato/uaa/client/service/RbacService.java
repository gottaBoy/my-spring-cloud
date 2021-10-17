/**
 * 
 */
package com.mysting.tomato.uaa.client.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

/** 
* 适用于zuul网关 应用服务API接口
*/
public interface RbacService {
	
	boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
