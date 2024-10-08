package com.user.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		//RestTemplate restTemplate = new RestTemplate();

		//List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

		//restTemplate.setInterceptors(interceptors);

		return new RestTemplate();

	}

}
