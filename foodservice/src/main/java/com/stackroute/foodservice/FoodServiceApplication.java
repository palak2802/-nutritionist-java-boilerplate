package com.stackroute.foodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.stackroute.foodservice.jwtfilter.JwtFilter;

@SpringBootApplication
public class FoodServiceApplication {
	
	@Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
    	final FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<JwtFilter>();
    	registrationBean.setFilter(new JwtFilter());
    	registrationBean.addUrlPatterns("/api/*");
    	return registrationBean;
    }
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
    
	public static void main(String[] args) {
		SpringApplication.run(FoodServiceApplication.class, args);
	}

}
