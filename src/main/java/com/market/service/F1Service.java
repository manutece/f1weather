package com.market.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.model.Sesion;

@Service
public class F1Service {

	@Value("${api.f1.url}")
	private String apiF1Url;
	
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	
	public F1Service(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
	}
	
	public Sesion getF1SessionData(String nombreCircuito, String tipoSesion, int year) {
		try {
			String url = UriComponentsBuilder.fromHttpUrl(apiF1Url)
				.pathSegment("sessions")
				.queryParam("circuit_short_name", nombreCircuito)
				.queryParam("session_name", tipoSesion)
				.queryParam("year", year)
				.toUriString();
			System.out.println(url);
			String sesionJson = restTemplate.getForObject(url, String.class);
			System.out.println(sesionJson);
			JsonNode sesionData = objectMapper.readTree(sesionJson).get(0);
			Sesion ret = objectMapper.treeToValue(sesionData, Sesion.class);
			System.out.println(ret.toString());
			return ret;
		
		} catch (Exception e) { 
			System.err.println("Error al obtener los datos de la sesion: " + e.getMessage());
			return null;
		}
	}

	public List<Sesion> getCircuitos(int año) {
		try {
			String url = UriComponentsBuilder.fromHttpUrl(apiF1Url)
				.pathSegment("sessions")
				.queryParam("year", año)
				.toUriString();
		
		ResponseEntity<Sesion[]> response = restTemplate.getForEntity(url, Sesion[].class);
		return Arrays.asList(response.getBody());
		
		} catch (Exception e) { 
			System.err.println("Error al obtener los datos de los circuitos: " + e.getMessage());
			return null;
		}
		
		
	}
}
