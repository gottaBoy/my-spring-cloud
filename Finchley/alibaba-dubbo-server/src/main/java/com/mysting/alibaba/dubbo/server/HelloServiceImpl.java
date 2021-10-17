package com.mysting.alibaba.dubbo.server;

import com.mysting.alibaba.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.Service;

// 注意：是 org.apache.dubbo.config.annotation.Service 注解
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "hello " + name;
    }

}