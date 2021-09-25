package com.mysting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@SpringBootApplication
@EnableDiscoveryClient
public class TurbineApplication {

	// http://localhost:8093/turbine.stream
	public static void main(String[] args) {
		SpringApplication.run(TurbineApplication.class, args);
	}

}
