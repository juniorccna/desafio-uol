package br.com.uol.project.first.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.project.first.entity.Customer;
import br.com.uol.project.first.entity.CustomerLocationInfo;
import br.com.uol.project.first.service.CustomerLocationInfoService;
import br.com.uol.project.first.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerLocationInfoService customerLocationInfoService;
	
	@GetMapping
	public List<Customer> findAll() {
		return customerService.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Customer save(@RequestBody @Valid Customer customer, HttpServletRequest request) {
		//String ipAddress = request.getRemoteAddr();
		String ipAddress = "177.55.154.15"; //IP SÃO PAULO
		Customer savedCustomer = customerService.save(customer);
		saveCustomerLocationInfo(savedCustomer, ipAddress);
		return savedCustomer;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return customerService.findById(id)
				.map(customer -> ResponseEntity.ok(customer))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Customer customer) {
		return customerService.findById(id)
				.map(saved -> {
					saved.setAge(customer.getAge());
					saved.setName(customer.getName());
					Customer updated = customerService.save(saved);
					return ResponseEntity.ok().body(updated);
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return customerService.findById(id)
				.map(customer -> { 
					customerService.delete(customer);
					return ResponseEntity.ok().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	private void saveCustomerLocationInfo(Customer customer, String ipAddress) {
		CustomerLocationInfo customerLocationInfo = new CustomerLocationInfo(ipAddress, customer);
		customerLocationInfoService.save(customerLocationInfo);
		log.info("saveCustomerLocationInfo {}", customerLocationInfo);
	}

}
