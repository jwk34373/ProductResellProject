package com.example.ProductResellProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProductResellProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductResellProjectApplication.class, args);
	}

}
