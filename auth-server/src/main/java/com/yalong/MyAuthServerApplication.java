package com.yalong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuyalong
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MyAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyAuthServerApplication.class, args);
	}

}
