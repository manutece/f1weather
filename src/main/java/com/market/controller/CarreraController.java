package com.market.controller;
import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;*/
import org.springframework.web.bind.annotation.*;
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
	public String getCarrera(@RequestParam String carreraNombre, @RequestParam int año) {
		return carreraService.getCarreraInfo(carreraNombre, año);
	}
	
}