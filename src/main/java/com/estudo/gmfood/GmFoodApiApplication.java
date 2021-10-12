package com.estudo.gmfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.estudo.gmfood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class GmFoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmFoodApiApplication.class, args);
	}

}
