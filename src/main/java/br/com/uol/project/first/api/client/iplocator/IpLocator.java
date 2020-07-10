package br.com.uol.project.first.api.client.iplocator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IpLocator {
	
	private String ipv4;
	
	@JsonProperty("continent_name")
	private String continentName;
	
	@JsonProperty("country_name")
	private String countryName;
	
	@JsonProperty("city_name")
	private String cityName;
	
	private String latitude;
	
	private String longitude;

}
