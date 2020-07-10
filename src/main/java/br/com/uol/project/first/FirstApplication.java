package br.com.uol.project.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FirstApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}
}
