package com.algaworks.algafood.api.v2.controller;


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

import com.algaworks.algafood.api.v2.assembler.CozinhaDtoAssemblerV2;
import com.algaworks.algafood.api.v2.controller.openapi.controller.CozinhaControllerOpenApiV2;
import com.algaworks.algafood.api.v2.disassembler.CozinhaInputDtoDisassemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaDtoV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputDtoV2;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerOpenApiV2{
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	@Autowired
	private CozinhaDtoAssemblerV2 cozinhaDtoAssembler;
	@Autowired
	private CozinhaInputDtoDisassemblerV2 cozinhaInputDtoDisassembler;
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	
	@GetMapping
	public PagedModel<CozinhaDtoV2> listar(@PageableDefault(size = 10) Pageable pageable){
		
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaDtoV2> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaDtoAssembler);

		return  cozinhasPagedModel;
	}
	
	
	@GetMapping("/{cozinhaId}")
	public CozinhaDtoV2 buscar(@PathVariable("cozinhaId") Long id){
		return cozinhaDtoAssembler.toModel(cadastroCozinha.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDtoV2 criar(@RequestBody @Valid CozinhaInputDtoV2 cozinhaInputDto){
		
		Cozinha cozinha = cadastroCozinha.salvar(cozinhaInputDtoDisassembler.cozinhaInputDtoToCidade(cozinhaInputDto));
		return cozinhaDtoAssembler.toModel(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaDtoV2 editar(@PathVariable("cozinhaId") Long id, @RequestBody @Valid CozinhaInputDtoV2 cozinhaInputDto){
		
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