package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.algaworks.algafood.api.assembler.CozinhaDtoAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaInputDtoDisassembler;
import com.algaworks.algafood.api.model.CozinhaDto;
import com.algaworks.algafood.api.model.CozinhasRepresentation;
import com.algaworks.algafood.api.model.input.CozinhaInputDto;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	@Autowired
	private CozinhaDtoAssembler cozinhaDtoAssembler;
	@Autowired
	private CozinhaInputDtoDisassembler cozinhaInputDtoDisassembler;
	
	
	@GetMapping
	public Page<CozinhaDto> listar(@PageableDefault(size = 10) Pageable pageable){
		
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		List<CozinhaDto> cozinhasDto = cozinhaDtoAssembler
				.cozinhasToListCozinhaDto(cozinhasPage.getContent());
		
		Page<CozinhaDto> cozinhasDtoPage = new PageImpl<CozinhaDto>(cozinhasDto, pageable, cozinhasPage.getTotalElements());
		
		return  cozinhasDtoPage;
	}
	
	@Deprecated
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasRepresentation listarXml(){
		return new CozinhasRepresentation(cozinhaRepository.findAll());
	}
	
	@GetMapping("/{cozinhaId}")
	public CozinhaDto buscar(@PathVariable("cozinhaId") Long id){
		return cozinhaDtoAssembler.cozinhaToCozinhaDto(cadastroCozinha.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDto criar(@RequestBody @Valid CozinhaInputDto cozinhaInputDto){
		
		Cozinha cozinha = cadastroCozinha.salvar(cozinhaInputDtoDisassembler.cozinhaInputDtoToCidade(cozinhaInputDto));
		return cozinhaDtoAssembler.cozinhaToCozinhaDto(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaDto editar(@PathVariable("cozinhaId") Long id, @RequestBody @Valid CozinhaInputDto cozinhaInputDto){
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
		
		cozinhaInputDtoDisassembler.copyToDomainObject(cozinhaInputDto, cozinhaAtual);
		
		cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
		
		return cozinhaDtoAssembler.cozinhaToCozinhaDto(cozinhaAtual);
		
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("cozinhaId") Long id){
		cadastroCozinha.excluir(id);
	}
}
