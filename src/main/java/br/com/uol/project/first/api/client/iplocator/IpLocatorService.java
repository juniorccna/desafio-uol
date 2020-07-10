package br.com.uol.project.first.api.client.iplocator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class IpLocatorService {
	
	@Autowired
	private IpLocatorClient client;
	
	public IpLocator findByIpv4(String ipv4) {
		if (StringUtils.isEmpty(ipv4)) {
			throw new IllegalArgumentException("ipv4 cannot be null or empty.");
		}
		try {
			IpLocatorResponse response = client.findByIpv4(ipv4);
			return response.getData();
		} catch (Exception e) {
			return null;
		}
	}

}
