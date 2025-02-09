package com.market.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
	
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	
	
	public WeatherService(HttpClient httpClient) {
		this.httpClient = httpClient;
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
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.GET()
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() != 200) {
				System.out.println("RESPONSE ERROR: CODIGO " + response.statusCode());
				return null;
			}
			
			String climaJson = response.body();
			JsonNode currentConditions = objectMapper.readTree(climaJson).path("currentConditions");
			
			Clima clima = new Clima();
			clima.setTemp(currentConditions.path("temp").asDouble());
			clima.setHumidity(currentConditions.path("humidity").asDouble());
			clima.setPrecip(currentConditions.path("precip").asDouble());
			clima.setPreciptype(currentConditions.path("preciptype").isMissingNode() ? null : currentConditions.path("preciptype").asText());
			clima.setWindspeed(currentConditions.path("windspeed").asDouble());
			clima.setVisibility(currentConditions.path("visibility").asDouble());
			
			return clima;
			
		} catch (Exception e){
			System.err.println("Error al obtener los datos del clima: " + e.getMessage());
			return null;
		}
		
		
	}
}
