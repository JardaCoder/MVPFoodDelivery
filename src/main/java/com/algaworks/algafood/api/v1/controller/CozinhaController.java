package com.algaworks.algafood.api.v1.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.CozinhaDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.api.v1.disassembler.CozinhaInputDtoDisassembler;
import com.algaworks.algafood.api.v1.model.CozinhaDto;
import com.algaworks.algafood.api.v1.model.input.CozinhaInputDto;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	@Autowired
	private CozinhaDtoAssembler cozinhaDtoAssembler;
	@Autowired
	private CozinhaInputDtoDisassembler cozinhaInputDtoDisassembler;
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	
	
	
	@GetMapping
	public PagedModel<CozinhaDto> listar(@PageableDefault(size = 10) Pageable pageable){
		
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaDto> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaDtoAssembler);

		return  cozinhasPagedModel;
	}
	
	
	@GetMapping("/{cozinhaId}")
	public CozinhaDto buscar(@PathVariable("cozinhaId") Long id){
		return cozinhaDtoAssembler.toModel(cadastroCozinha.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDto criar(@RequestBody @Valid CozinhaInputDto cozinhaInputDto){
		
		Cozinha cozinha = cadastroCozinha.salvar(cozinhaInputDtoDisassembler.cozinhaInputDtoToCidade(cozinhaInputDto));
		return cozinhaDtoAssembler.toModel(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaDto editar(@PathVariable("cozinhaId") Long id, @RequestBody @Valid CozinhaInputDto cozinhaInputDto){
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
		
		cozinhaInputDtoDisassembler.copyToDomainObject(cozinhaInputDto, cozinhaAtual);
		
		cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
		
		return cozinhaDtoAssembler.toModel(cozinhaAtual);
		
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("cozinhaId") Long id){
		cadastroCozinha.excluir(id);
	}
}

//	@Deprecated
//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public CozinhasRepresentation listarXml(){
//		return new CozinhasRepresentation(cozinhaRepository.findAll());
//	}
