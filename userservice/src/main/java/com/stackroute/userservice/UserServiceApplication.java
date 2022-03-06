package com.stackroute.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.stackroute.userservice.jwtfilter.JwtFilter;

@EnableEurekaClient 
@SpringBootApplication
public class UserServiceApplication {

	@Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
    	final FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<JwtFilter>();
    	registrationBean.setFilter(new JwtFilter());
    	registrationBean.addUrlPatterns("/api/*");
    	return registrationBean;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
