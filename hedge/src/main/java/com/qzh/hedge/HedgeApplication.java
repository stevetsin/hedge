package com.qzh.hedge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HedgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HedgeApplication.class, args);
	}
}
