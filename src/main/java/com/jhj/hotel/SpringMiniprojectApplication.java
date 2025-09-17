package com.jhj.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringMiniprojectApplication extends SpringBootServletInitializer{

	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(SpringMiniprojectApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringMiniprojectApplication.class, args);
	}

}
