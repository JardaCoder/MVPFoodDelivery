package com.algaworks.algafood.client.api;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.api.model.RestauranteDto;
import com.algaworks.algafood.api.model.input.RestauranteInputDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestauranteClient {
	
	
	private String url;
	private RestTemplate restTemplate;
	
	private static final String RESOURCE_PATH = "/restaurantes";

	public List<RestauranteDto> listar(){
		
		try {
			URI resourceUri = URI.create(url + RESOURCE_PATH);
			
			
			var restaurantesDto = restTemplate.getForObject(resourceUri, RestauranteDto[].class);
			
			return Arrays.asList(restaurantesDto);
			
		} catch (RestClientResponseException e) {
			throw new ClientApiException(e.getMessage(), e);
		}
		
	}
	

	public RestauranteInputDto salvar(RestauranteInputDto restaurante){
		
		URI resourceUri = URI.create(url + RESOURCE_PATH);
		
		try {
			return restTemplate.postForObject(resourceUri, restaurante,  RestauranteInputDto.class);
			
		} catch (RestClientResponseException e) {
			throw new ClientApiException(e.getMessage(), e);
		}
	}
}
