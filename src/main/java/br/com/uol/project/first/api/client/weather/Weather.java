package br.com.uol.project.first.api.client.weather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Weather {
	
	private String title;
	
	@JsonProperty("location_type")
	private String locationType;
	
	private Integer woeid;
	
	private Integer distance;
	
	@JsonProperty("consolidated_weather")
	private List<WeatherDetail> weatherDetails;
	
}
