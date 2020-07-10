package br.com.uol.project.first.api.client.weather;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://www.metaweather.com/", name = "weather")
public interface WeatherClient {
	
	@GetMapping("/api/location/search")
	public List<Weather> findByLatLong(@RequestParam(name = "lattlong", required = true) String lattlong);
	
	@GetMapping("/api/location/{woeid}/")
	public Weather findByWoeid(@PathVariable Integer woeid);

}
