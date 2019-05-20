package com.celinamax.easyfood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.celinamax.easyfood.services.S3Service;

@SpringBootApplication
public class EasyFoodApplication implements CommandLineRunner{		

	public static void main(String[] args) {
		SpringApplication.run(EasyFoodApplication.class, args);		
	}

	@Override
	public void run(String... args) throws Exception {	
		
	}
}
