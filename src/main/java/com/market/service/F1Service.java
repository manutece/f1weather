package com.market.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class F1Service {

	@Value("${api.f1.url}")
	private String apiF1Url;
	
	private final RestTemplate restTemplate;
	
	public F1Service(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public String getF1SessionData(int circuitKey, int year) {
		String url = UriComponentsBuilder.fromHttpUrl(apiF1Url)
				.pathSegment("sessions")
				.queryParam("circuit_key", circuitKey)
				.queryParam("year", year)
				.toUriString();
		System.out.println(url);
		
		return restTemplate.getForObject(url, String.class);
	}
}
