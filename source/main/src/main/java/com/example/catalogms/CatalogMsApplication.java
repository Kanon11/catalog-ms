package com.example.catalogms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CatalogMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogMsApplication.class, args);
	}

}
