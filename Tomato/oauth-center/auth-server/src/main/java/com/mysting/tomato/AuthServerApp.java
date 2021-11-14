/**
 * 
 */
package com.mysting.tomato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import com.mysting.tomato.common.feign.GlobalFeignConfig;
import com.mysting.tomato.common.port.PortApplicationEnvironmentPreparedEventListener;
import com.mysting.tomato.log.annotation.EnableLogging;
import com.mysting.tomato.uaa.server.UAAServerConfig;

@EnableLogging
@EnableDiscoveryClient
@SpringBootApplication
@Import(UAAServerConfig.class)
@EnableFeignClients(defaultConfiguration= GlobalFeignConfig.class)
public class AuthServerApp {
	
	public static void main(String[] args) {
//		固定端口启动
//		SpringApplication.run(OpenAuthServerApp.class, args);
		
		//随机端口启动
		SpringApplication app = new SpringApplication(AuthServerApp.class);
        app.addListeners(new PortApplicationEnvironmentPreparedEventListener());
        app.run(args);
		
	}

}
