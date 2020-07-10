package br.com.uol.project.first.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uol.project.first.api.client.iplocator.IpLocator;
import br.com.uol.project.first.api.client.iplocator.IpLocatorService;
import br.com.uol.project.first.api.client.weather.Weather;
import br.com.uol.project.first.api.client.weather.WeatherDetail;
import br.com.uol.project.first.api.client.weather.WeatherService;
import br.com.uol.project.first.config.RabbitMQConfig;
import br.com.uol.project.first.entity.CustomerLocationInfo;
import br.com.uol.project.first.entity.WeatherInfo;
import br.com.uol.project.first.repository.CustomerLocationInfoRepository;
import br.com.uol.project.first.service.CustomerLocationInfoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerLocationInfoServiceImpl implements CustomerLocationInfoService {

	@Autowired
	private CustomerLocationInfoRepository customerLocationInfoRepository;
	
	@Autowired
	private IpLocatorService ipLocatorService;
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public List<CustomerLocationInfo> findAll() {
		return customerLocationInfoRepository.findAll();
	}

	@Override
	public CustomerLocationInfo save(CustomerLocationInfo customerLocationInfo) {
		CustomerLocationInfo locationInfoSaved = customerLocationInfoRepository.save(customerLocationInfo);
		if ( locationInfoSaved.getWeatherInfo() == null ) {
			this.sendToRabbitQueue(customerLocationInfo);
		}
		return locationInfoSaved;
	}

	@Override
	public Optional<CustomerLocationInfo> findById(Long id) {
		return customerLocationInfoRepository.findById(id);
	}

	@Override
	public void delete(CustomerLocationInfo customerLocationInfo) {
		customerLocationInfoRepository.delete(customerLocationInfo);
	}
	
	private void sendToRabbitQueue(CustomerLocationInfo customerLocationInfo) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, customerLocationInfo);
	}

	
	private CustomerLocationInfo fetchCompleteCustomerLocationInfo(String ipv4) {
		CustomerLocationInfo locationInfo = null;
		try {
			IpLocator ipLocator = ipLocatorService.findByIpv4(ipv4);
			if (ipLocator != null) {
				locationInfo = new CustomerLocationInfo(
						ipv4,
						ipLocator.getCityName(),
						ipLocator.getCountryName(),
						ipLocator.getLatitude(),
						ipLocator.getLongitude());
				
				locationInfo.setWeatherInfo(
						getWeatherInfoToday(ipLocator.getLatitude(), ipLocator.getLongitude()));
			}
		} catch( Exception ex ) {
			log.error(ex.getLocalizedMessage());
		}
		return locationInfo;
	}
	
	
	private WeatherInfo getWeatherInfoToday(String latitude, String longitude) {
		Weather weather = weatherService.fetchWeatherTodayByLattLong(latitude, longitude);
		Optional<WeatherDetail> weatherDetail = weather.getWeatherDetails()
				.stream()
				.filter(w -> w.getApplicableDate().isEqual(LocalDate.now()))
				.findFirst();
		
		return weatherDetail
				.map(wd -> {
					return new WeatherInfo(wd.getApplicableDate(), wd.getMinTemp(), wd.getMaxTemp());
				})
				.orElse(null);
	}
	
	@RabbitListener(queues = "${first.rabbitmq.queueName}")
	public void fetchAndSaveCompleteCustomerInfo(CustomerLocationInfo customerLocationInfo) {
		CustomerLocationInfo completeCustomerLocationInfo = fetchCompleteCustomerLocationInfo(customerLocationInfo.getIpAdrress());
		completeCustomerLocationInfo.setId(customerLocationInfo.getId());
		customerLocationInfoRepository.save(completeCustomerLocationInfo);
		log.info("#### fetchAndSaveCompleteCustomerInfo {}", completeCustomerLocationInfo);
	}

}
