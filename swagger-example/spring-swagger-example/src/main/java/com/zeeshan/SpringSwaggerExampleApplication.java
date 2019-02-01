package com.zeeshan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSwaggerExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSwaggerExampleApplication.class, args);

		public String dummy(){
			return "dummy";
	}
}
//very initial file to work with
//run application as 
//http://localhost:9090/swagger-ui.html