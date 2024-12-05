package com.market.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.market.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
	
	@Autowired
	private WeatherService weatherService;
	
	
	@GetMapping("/test")
	public String testEndpoint() {
		return weatherService.getWeatherData("London, UK", "2020-12-15T05:00:00");
	}
	
}
