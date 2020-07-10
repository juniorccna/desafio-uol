package br.com.uol.project.first.service;

import java.util.List;
import java.util.Optional;

import br.com.uol.project.first.entity.Customer;


public interface CustomerService {
	
	public List<Customer> findAll();
	
	public Customer save(Customer customer);
	
	public Optional<Customer> findById(Long id);
	
	public void delete(Customer customer);

}
