package com.algaworks.algafood.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.api.model.input.CidadeIdInputDto;
import com.algaworks.algafood.api.model.input.CozinhaIdInputDto;
import com.algaworks.algafood.api.model.input.EnderecoInputDto;
import com.algaworks.algafood.api.model.input.RestauranteInputDto;
import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestauranteClient;

public class ListagemRestauranteMain {

	public static void main(String[] args) {
		
		try {
			
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			interceptors.add(new ClientConfig("Accept", MediaType.APPLICATION_JSON_VALUE));

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setInterceptors(interceptors);
			
			RestauranteClient restauranteClient = new RestauranteClient("http://api.jardafood.local:8080", restTemplate);
			
			restauranteClient.listar().stream().forEach(restaurante -> System.out.println(restaurante.getNome()));
			
			restauranteClient.salvar(ListagemRestauranteMain.montarRestauranteInput());
			
		} catch (ClientApiException e) {
			if(e.getProblem() != null) {
				System.out.println(e.getProblem().getUserMessage());
			}else {
				System.out.println("Erro desconhecido");
			}
		}
		
		
	}
	
	public static RestauranteInputDto montarRestauranteInput() {
		RestauranteInputDto restauranteInputDto = new RestauranteInputDto();
		
		restauranteInputDto.setNome("Java client");
		restauranteInputDto.setTaxaFrete(BigDecimal.TEN);
		
		CozinhaIdInputDto cozinha = new CozinhaIdInputDto();
		cozinha.setId(1L);
		
		CidadeIdInputDto cidade = new CidadeIdInputDto();
		cidade.setId(1L);
		
		EnderecoInputDto endereco = new EnderecoInputDto();
		
		endereco.setCep("88360-000");
		endereco.setLogradouro("Rua andré palmeira");
		endereco.setNumero("200");
		endereco.setBairro("São pedro");
		
		endereco.setCidade(cidade);
		
		restauranteInputDto.setCozinha(cozinha);
		
		restauranteInputDto.setEndereco(endereco);
		
		return restauranteInputDto;
	}
	
	
}
