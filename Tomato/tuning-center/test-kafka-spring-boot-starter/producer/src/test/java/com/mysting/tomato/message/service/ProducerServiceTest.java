package com.mysting.tomato.message.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysting.tomato.message.MessageApp;

import lombok.extern.slf4j.Slf4j;

 
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
		MessageApp.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 配置启动类
public class ProducerServiceTest {
	
	@Resource
	private ProducerService producerService ;
	
	@Test
	public void test() {
		
		Map map = new HashMap();
		map.put("msg", "hello");
		producerService.sendMsg(map);
	}

	
}
