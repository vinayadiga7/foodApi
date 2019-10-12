package com.vinay.foodCourt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.FoodCourt.Controllers.LoginController;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.FoodCourt.Repositories")
@EntityScan("com.FoodCourt.Entities")
@ComponentScan(basePackages = {"com.FoodCourt.Controllers","com.FoodCourt.Services"})
public class FoodCourtAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodCourtAppApplication.class, args);
	}

}
