package com.market.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.util.URLEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.model.Clima;
import com.market.model.Sesion;

@Service
public class CarreraService {
	
	private final F1Service f1Service;
	private final WeatherService weatherService;
	
	public CarreraService(F1Service f1Service, WeatherService weatherService) {
		this.f1Service = f1Service;
		this.weatherService = weatherService;		
	}
	
	public Sesion getCarreraInfo(String circuito, String tipoSesion, int año) {
		System.out.println("PRE FETCH F1");
		Sesion carreraData = f1Service.getF1SessionData(circuito, tipoSesion, año);
		String ciudad = "" + carreraData.getLocation() + "," +carreraData.getCountry_name();
		String fecha = carreraData.getDate_start();
		
		System.out.println("Fecha: " + fecha + "\nLocacion: " + ciudad);
		Clima weatherData = weatherService.getWeatherData(ciudad, fecha.replace("+00:00", ""));
		System.out.println("Despues de clima");
		carreraData.setClima(weatherData);
		
		return carreraData;
	}

	public List<String> getCircuitos(int año) {
		List<Sesion> sesiones = f1Service.getCircuitos(año);
		return sesiones.stream()
				.map(Sesion::getCircuit_short_name)
				.distinct()
				.collect(Collectors.toList());
	}


}
