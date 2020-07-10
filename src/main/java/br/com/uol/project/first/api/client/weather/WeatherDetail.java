package br.com.uol.project.first.api.client.weather;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WeatherDetail {

	private Long id;
	
	private LocalDateTime created;
	
	@JsonProperty("applicable_date")
	private LocalDate applicableDate;
	
	@JsonProperty("min_temp")
	private Float minTemp;
	
	@JsonProperty("max_temp")
	private Float maxTemp;
}
