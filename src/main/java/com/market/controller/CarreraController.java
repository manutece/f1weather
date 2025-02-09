package com.market.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;*/
import org.springframework.web.bind.annotation.*;

import com.market.model.Sesion;
import com.market.service.CarreraService;

@RestController
@RequestMapping("/api/f1")
public class CarreraController {
	
	@Autowired
	private final CarreraService carreraService;
	
	public CarreraController(CarreraService carreraService) {
		this.carreraService = carreraService;
	}
	
	@GetMapping("/carrera")
	public ResponseEntity<Sesion> getCarrera(@RequestParam String circuitoNombre, @RequestParam String tipoSesion, @RequestParam int año) {
		try {
			Sesion sesion = carreraService.getCarreraInfo(circuitoNombre, tipoSesion, año);
			return ResponseEntity.ok(sesion);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/circuitos")
	public ResponseEntity<List<String>> getCircuitosPorAño(@RequestParam int año) {
		try {
			List<String> circuitos = carreraService.getCircuitos(año);
			return ResponseEntity.ok(circuitos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}
