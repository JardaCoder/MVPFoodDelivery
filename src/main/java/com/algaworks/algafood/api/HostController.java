package com.algaworks.algafood.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HostController {

	@GetMapping("/hostcheck")
	public Map<String, String> check() throws UnknownHostException {
		
		var localHost = InetAddress.getLocalHost(); 
		return Map.of("host", localHost.getHostAddress(), "name", localHost.getHostName(), "quemLeu", "Mamou minha pica");
	}
}
