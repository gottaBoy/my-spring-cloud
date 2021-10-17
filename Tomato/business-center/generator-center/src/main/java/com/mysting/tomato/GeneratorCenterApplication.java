package com.mysting.tomato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

import com.mysting.tomato.log.annotation.EnableLogging;
/**
 * 启动类
 * @Author: [dawei QQ:64738479]
 * @Date: [2019-04-25 21:48]
 * @Description: [ ]
 * @Version: [1.0.1]
 * @Copy: [com.zzg]
 */
@Configuration
@EnableLogging
@EnableDiscoveryClient
@SpringBootApplication
public class GeneratorCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(GeneratorCenterApplication.class, args);
	}
}
