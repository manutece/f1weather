package com.market.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.market")
public class WeatherF1ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherF1ApiApplication.class, args);
	}

}
