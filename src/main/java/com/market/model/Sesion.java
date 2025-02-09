package com.market.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.AccessLevel;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sesion {
	
	private String circuit_short_name;
    private String session_name;
    private String date_start;
    private String country_name;
    private Clima clima;
    
    @Getter(AccessLevel.NONE)
    private String location;
    
    public String getLocation() {
    	if (location.equals("São Paulo")) {
            return "Sao Paulo";
        } else if (location.equals("Montréal")) {
            return "Montreal";
        } else {
        	return this.location;
        }
    }
}
