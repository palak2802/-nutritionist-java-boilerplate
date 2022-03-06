package com.stackroute.foodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.stackroute.foodservice.jwtfilter.JwtFilter;

@EnableEurekaClient 
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
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(FoodServiceApplication.class, args);
	}

}
