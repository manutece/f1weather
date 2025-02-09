package com.market.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.model.Sesion;

@Service
public class F1Service {

	@Value("${api.f1.url}")
	private String apiF1Url;
	
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	
	public F1Service(HttpClient httpClient) {
		this.httpClient = httpClient;
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

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.GET()
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() != 200) {
				System.out.println("RESPONSE ERROR: CODIGO " + response.statusCode());
				return null;
			}
			
			String sesionJson = response.body();
            JsonNode sesionData = objectMapper.readTree(sesionJson).get(0);
            Sesion ret = objectMapper.treeToValue(sesionData, Sesion.class);
			
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
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.GET()
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() != 200) {
				System.out.println("RESPONSE: " + response);
				return null;
			}
			
			Sesion[] sesionesArray = objectMapper.readValue(response.body(), Sesion[].class);
			return Arrays.asList(sesionesArray);
		
		} catch (Exception e) { 
			System.err.println("Error al obtener los datos de los circuitos: " + e.getMessage());
			return null;
		}
		
	}
}
