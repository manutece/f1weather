package com.market.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {
	
	@Value("${api.weather.url}")
	private String apiUrl;
	
	@Value("${weather.api.key}")
	private String apiKey;
	
	private final RestTemplate restTemplate;
	
	public WeatherService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public String getWeatherData(String locacion, String fecha) {
		try {
			String elementos = "datetime,temp,tempmin,tempmax,humidity,icon,precip,preciptype,visibility,windspeed";
			String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
					.pathSegment(locacion, fecha)
					.queryParam("key", apiKey)
					.queryParam("unitGroup", "metric")
					.queryParam("include", "current")
					.queryParam("elements", elementos)
					.toUriString();
			
			System.out.println(url);	
			return restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
			System.err.println("Error al obtener los datos del clima: " + e.getMessage());
			return null;
		}
	}
}
