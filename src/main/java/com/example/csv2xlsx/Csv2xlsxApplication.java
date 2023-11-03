package com.example.csv2xlsx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.csv2xlsx")
public class Csv2xlsxApplication {

	public static void main(String[] args) {
		SpringApplication.run(Csv2xlsxApplication.class, args);
	}

}
