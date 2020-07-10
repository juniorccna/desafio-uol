package br.com.uol.project.first.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "weather_info")
@NoArgsConstructor
public class WeatherInfo {
	
	@Id
	@Column(name = "weather_info_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "applicable_date")
	private LocalDate applicableDate;
	
	@Column(name = "temp_min")
	private Float tempMin;
	
	@Column(name = "temp_max")
	private Float tempMax;

	public WeatherInfo(LocalDate applicableDate, Float tempMin, Float tempMax) {
		super();
		this.applicableDate = applicableDate;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
	}

}
