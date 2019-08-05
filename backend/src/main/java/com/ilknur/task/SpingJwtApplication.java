package com.ilknur.task;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ilknur.task.service.AccountService;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@SpringBootApplication
public class SpingJwtApplication  implements CommandLineRunner{
	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(SpingJwtApplication.class, args);
		
	

	}
	

	@Override
	public void run(String... args) throws Exception {

	}
	
	@Bean
	public BCryptPasswordEncoder getBCPE() {

		return new BCryptPasswordEncoder();
	}

	
}
