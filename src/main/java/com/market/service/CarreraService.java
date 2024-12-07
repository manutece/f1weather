package com.market.service;

import org.springframework.stereotype.Service;

@Service
public class CarreraService {
	
	private final F1Service f1Service;
	private final WeatherService weatherService;
	
	public CarreraService(F1Service f1Service, WeatherService weatherService) {
		this.f1Service = f1Service;
		this.weatherService = weatherService;		
	}
	
	public String getCarreraInfo(String carrera, int año) {
		System.out.println("ARRANCA GET CARRERA\n");
		int carreraKey = getCarreraKey(carrera);
		System.out.println("PRE FETCH F1");
		String carreraData = f1Service.getF1SessionData(carreraKey, año);
		
		String ciudad = getCiudad(carreraData);
		String fecha = getFecha(carreraData);
		
		System.out.println("PRE FETCH WEATHER");
		String weatherData = weatherService.getWeatherData(ciudad, fecha);
		
		return String.format("Carrera: %s\nClima: %s", carreraData, weatherData);
	}

	private int getCarreraKey(String carrera) {
		System.out.println("GET CARRERA KEY\n");
		return 150;
	}

	private String getFecha(String carreraData) {
		//tener en cuenta gmt_offset
		System.out.println("GET FECHA\n");
		return "2020-12-15T05:00:00";
	}

	private String getCiudad(String carreraData) {
		System.out.println("GET CIUDAD\n");
		return "London, UK";
	}

}
