package br.com.uol.project.first.api.client.iplocator;

import lombok.Data;

@Data
public class IpLocatorResponse {
	
	private String status;
	
	private IpLocator data;

}
