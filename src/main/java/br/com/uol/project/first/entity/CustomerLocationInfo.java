package br.com.uol.project.first.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "customer_location_info")
@NoArgsConstructor
public class CustomerLocationInfo {
	
	@Id
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "weather_info_id")
	private WeatherInfo weatherInfo;
	
	@Column(name = "ip_address")
	private String ipAdrress;
	
	@Column(name = "city_name")
	private String cityName;
	
	@Column(name = "country_name")
	private String countryName;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	public CustomerLocationInfo(String ipAddress, Customer customer) {
		this.ipAdrress = ipAddress;
		this.customer = customer;
	}

	public CustomerLocationInfo(String ipAdrress, String cityName, String countryName, String latitude,
			String longitude) {
		super();
		this.ipAdrress = ipAdrress;
		this.cityName = cityName;
		this.countryName = countryName;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
}
