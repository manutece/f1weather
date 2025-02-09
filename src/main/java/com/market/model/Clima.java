package com.market.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clima {
	private double temp;
	private double humidity;
	private double precip;
	private String preciptype;
	private double windspeed;
	private double visibility;
	
}
