package com.example.eureka_server_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerMainApplication.class, args);
	}

}
