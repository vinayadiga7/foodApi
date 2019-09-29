package com.vinay.foodCourt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.FoodCourt.Login.LoginController;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@ComponentScan(basePackageClasses= LoginController.class)
public class FoodCourtAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodCourtAppApplication.class, args);
	}

}
