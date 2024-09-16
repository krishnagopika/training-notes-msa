package com.revature.revshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	public WebClient webClient() {
		
		return WebClient.builder()
				.baseUrl("http://inventory-service:8083/api/inventory/")
				.build();
	}

}