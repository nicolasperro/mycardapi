package com.mycard.mycardapi;

import org.springframework.boot.SpringApplication;

import org.springframework.context.annotation.Bean; // Importe esta classe
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importe esta classe
import org.springframework.security.crypto.password.PasswordEncoder; // Importe esta classe


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MycardapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycardapiApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();}

}
