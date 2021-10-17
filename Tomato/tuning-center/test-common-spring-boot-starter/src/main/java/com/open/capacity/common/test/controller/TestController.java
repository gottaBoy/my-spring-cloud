package com.mysting.tomato.common.test.controller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysting.tomato.common.annotation.AccessLimit;
import com.mysting.tomato.common.test.service.LogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@Autowired
	 BeanFactory beanFactory;
	 
	 @Autowired
	 LogService logService;
	 
	
	@GetMapping("/test")
	@AccessLimit(seconds=1,maxCount=2,needLogin=false)
	public String opt(){
		
		logService.log();

		return "ok" ;
	}
	
	 
	
	 
}
