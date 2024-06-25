package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients   //44分
public class LoadbalancerServiceB8081Application {

	public static void main(String[] args) {
		SpringApplication.run(LoadbalancerServiceB8081Application.class, args);
	}

}
