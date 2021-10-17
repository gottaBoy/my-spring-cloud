/**
 * 
 */
package com.mysting.tomato;

import com.mysting.tomato.common.annotation.EnableApiIdempotent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

import com.mysting.tomato.common.port.PortApplicationEnvironmentPreparedEventListener;
import com.mysting.tomato.log.annotation.EnableLogging;

 
@Configuration
@EnableLogging
@EnableDiscoveryClient
@SpringBootApplication
@EnableApiIdempotent
public class UserCenterApp {
	
	public static void main(String[] args) {
//		固定端口启动
//		SpringApplication.run(UserCenterApp.class, args);
		
		//随机端口启动
		SpringApplication app = new SpringApplication(UserCenterApp.class);
        app.addListeners(new PortApplicationEnvironmentPreparedEventListener());
        app.run(args);
		
	}

}
