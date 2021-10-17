package com.mysting.tomato.uaa.service.impl;

import com.mysting.tomato.uaa.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mysting.tomato.common.auth.details.LoginAppUser;
import com.mysting.tomato.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@SuppressWarnings("all")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginAppUser loginAppUser = null;

      
        if (StringUtil.isPhone(username)){
        	  //手机
            loginAppUser = userFeignClient.findByMobile(username);
        }else {
            // 用户名
            loginAppUser = userFeignClient.findByUsername(username);   			  
        }

        if (loginAppUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }else if (StringUtil.isBlank(loginAppUser.getUsername())) {
        	throw new ProviderNotFoundException("系统繁忙中");
        }
        else if (!loginAppUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }

        return loginAppUser;
    }


     
}
