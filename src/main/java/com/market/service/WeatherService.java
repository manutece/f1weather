package com.market.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.model.Clima;

@Service
public class WeatherService {
	
	@Value("${api.weather.url}")
	private String apiUrl;
	
	@Value("${weather.api.key}")
	private String apiKey;
	
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	
	
	public WeatherService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
	}
	
	public Clima getWeatherData(String locacion, String fecha) {
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
			String climaJson = restTemplate.getForObject(url, String.class);
			JsonNode climaNodo = objectMapper.readTree(climaJson);
			JsonNode currentconditionsNodo = climaNodo.path("currentConditions");
			
			Clima clima = new Clima();
			clima.setTemp(currentconditionsNodo.path("temp").asDouble());
			clima.setHumidity(currentconditionsNodo.path("humidity").asDouble());
			clima.setPrecip(currentconditionsNodo.path("precip").asDouble());
			clima.setPreciptype(currentconditionsNodo.path("").isNull() ? null : currentconditionsNodo.path("preciptype").asText());
			clima.setWindspeed(currentconditionsNodo.path("windspeed").asDouble());
			clima.setVisibility(currentconditionsNodo.path("visibility").asDouble());
					
			return clima;
		} catch (Exception e) {
			System.err.println("Error al obtener los datos del clima: " + e.getMessage());
			return null;
		}
	}
}
