package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.api.v1.disassembler.CidadeInputDtoDisassembler;
import com.algaworks.algafood.api.v1.model.CidadeDto;
import com.algaworks.algafood.api.v1.model.input.CidadeInputDto;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;




@RestController
@RequestMapping(value = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	CadastroCidadeService cadastroCidade;
	@Autowired
	CidadeInputDtoDisassembler cidadeInputDtoDisassembler;
	@Autowired
	CidadeDtoAssembler cidadeDtoAssembler;

	
	@Deprecated
	@GetMapping
	public CollectionModel<CidadeDto> listar() {
		return cidadeDtoAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	
	@Deprecated
	@GetMapping("/{cidadeId}")
	public CidadeDto buscar(@PathVariable("cidadeId") Long id) {
		return  cidadeDtoAssembler.toModel(cadastroCidade.buscarOuFalhar(id));
	}

	@Deprecated
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto criar(@RequestBody @Valid  CidadeInputDto cidadeInputDtp ) {
		try {
				
			Cidade cidade = cidadeInputDtoDisassembler.cidadeInputDtoToCidade(cidadeInputDtp);
			cadastroCidade.salvar(cidade);
			CidadeDto cidadeDto = cidadeDtoAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriResponseHeader(cidadeDto.getId());
			
			return cidadeDto;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Deprecated
	@PutMapping("/{cidadeId}")
	public CidadeDto editar(@PathVariable("cidadeId") Long id, @RequestBody @Valid CidadeInputDto cidadeInputDto) {
		
		try {			
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
			
			cidadeInputDtoDisassembler.copyToDomainObject(cidadeInputDto, cidadeAtual);
			
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
			
			return cidadeDtoAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Deprecated
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("cidadeId") Long id) {
		
		cadastroCidade.excluir(id);
	}
}
