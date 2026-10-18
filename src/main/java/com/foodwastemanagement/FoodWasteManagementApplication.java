package com.foodwastemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodWasteManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodWasteManagementApplication.class, args);
	}

}
