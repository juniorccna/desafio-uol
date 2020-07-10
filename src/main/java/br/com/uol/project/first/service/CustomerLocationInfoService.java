package br.com.uol.project.first.service;

import java.util.List;
import java.util.Optional;

import br.com.uol.project.first.entity.CustomerLocationInfo;


public interface CustomerLocationInfoService {
	
	public List<CustomerLocationInfo> findAll();
	
	public CustomerLocationInfo save(CustomerLocationInfo customerLocationInfo);
	
	public Optional<CustomerLocationInfo> findById(Long id);
	
	public void delete(CustomerLocationInfo customerLocationInfo);

}
