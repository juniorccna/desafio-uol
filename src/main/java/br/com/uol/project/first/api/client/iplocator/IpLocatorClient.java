package br.com.uol.project.first.api.client.iplocator;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "https://ipvigilante.com/", name = "ip-locator")
public interface IpLocatorClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/{ipv4}")
	public IpLocatorResponse findByIpv4(@PathVariable String ipv4);

}
