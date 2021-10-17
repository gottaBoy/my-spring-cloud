package com.mysting.tomato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.mysting.tomato.service.SendService;

@Configuration
@SpringBootApplication
public class ProducerApp {
	public static void main(String[] args) {
		
		
//		固定端口启动
		ConfigurableApplicationContext context = SpringApplication.run(ProducerApp.class, args);
	}
}
