package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.core.security.SecurityUtils;

import io.swagger.annotations.Api;

@Api(tags = "RootEntryPoint")
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {
	
	
	@Autowired
	private JardaLinks jardaLinks;
	@Autowired
	private SecurityUtils securityUtils;
	
	@GetMapping
	public RooEntryPointDto root() {
		var rootEntryPointDto = new RooEntryPointDto();
		
		if(securityUtils.podeConsultarCozinhas()) {
			rootEntryPointDto.add(jardaLinks.linkToCozinhas("cozinhas"));
		}
		
		if(securityUtils.podePesquisarPedidos()) {
			rootEntryPointDto.add(jardaLinks.linkToPedidos("pedidos"));
		}
		
		if(securityUtils.podeConsultarRestaurantes()) {
			rootEntryPointDto.add(jardaLinks.linkToRestaurantes("restaurantes"));
		}
		
		if(securityUtils.podeConsultarUsuariosGruposPermissoes()) {
			rootEntryPointDto.add(jardaLinks.linkToGrupos("grupos"));
			rootEntryPointDto.add(jardaLinks.linkToUsuarios("usuarios"));
			rootEntryPointDto.add(jardaLinks.linkToPermissoes("permissoes"));
		}
		
		if(securityUtils.podeConsultarFormasPagamento()) {
			rootEntryPointDto.add(jardaLinks.linkToFormasPagamento("formas-pagamento"));
		}
		
		if(securityUtils.podeConsultarEstados()) {
			rootEntryPointDto.add(jardaLinks.linkToEstados("estados"));
		}
		
		if(securityUtils.podeConsultarCidades()) {
			rootEntryPointDto.add(jardaLinks.linkToCidades("cidades"));
		}
		
		if(securityUtils.podeConsultarEstatisticas()) {
			rootEntryPointDto.add(jardaLinks.linkToEstatisticas("estatisticas"));
		}
 		
		return rootEntryPointDto;
	}
	
	
	private static class RooEntryPointDto extends RepresentationModel<RooEntryPointDto>{
		
	}
	
	public static class EstatisticasDto extends RepresentationModel<EstatisticasDto> {
	}


}
