package com.ezbytes.SpringbootSecuritydemo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityDemoApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

}
