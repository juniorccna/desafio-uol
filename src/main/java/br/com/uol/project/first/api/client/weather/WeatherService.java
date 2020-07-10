package br.com.uol.project.first.api.client.weather;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WeatherService {
	
	@Autowired
	private WeatherClient client;
	
	
	public Weather fetchWeatherTodayByLattLong(String latitude, String longitude) {
		if (StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)) {
			throw new IllegalArgumentException("latitude and longitude cannot be null or empty.");
		}
		
		List<Weather> weathers = client.findByLatLong(String.format("%s,%s", latitude, longitude)); //Exec chamada na API buscando por LAT e LONG
		
		return getNearestWeatherLocation(weathers) //Filtra o mais próximo
				.map(foundWeather -> client.findByWoeid(foundWeather.getWoeid()))//Recupera as informações completas
				.orElse(null);
	}
	
	
	
	private Optional<Weather> getNearestWeatherLocation(List<Weather> weathers) {
		return weathers
				.stream()
				.sorted(Comparator.comparingInt(Weather::getDistance))
				.findFirst();
	}

}
