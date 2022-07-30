package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.JardaLinks;

import io.swagger.annotations.Api;

@Api(tags = "RootEntryPoint")
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {
	
	
	@Autowired
	private JardaLinks jardaLinks;
	
	@GetMapping
	public RooEntryPointDto root() {
		var rootEntryPointDto = new RooEntryPointDto();
		
		rootEntryPointDto.add(jardaLinks.linkToCozinhas("cozinhas"));
		rootEntryPointDto.add(jardaLinks.linkToPedidos("pedidos"));
		rootEntryPointDto.add(jardaLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointDto.add(jardaLinks.linkToGrupos("grupos"));
		rootEntryPointDto.add(jardaLinks.linkToUsuarios("usuarios"));
		rootEntryPointDto.add(jardaLinks.linkToPermissoes("permissoes"));
		rootEntryPointDto.add(jardaLinks.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointDto.add(jardaLinks.linkToEstados("estados"));
		rootEntryPointDto.add(jardaLinks.linkToCidades("cidades"));
		rootEntryPointDto.add(jardaLinks.linkToEstatisticas("estatisticas"));
		
		return rootEntryPointDto;
	}
	
	
	private static class RooEntryPointDto extends RepresentationModel<RooEntryPointDto>{
		
	}
	
	public static class EstatisticasDto extends RepresentationModel<EstatisticasDto> {
	}


}
