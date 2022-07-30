package com.algaworks.algafood.api.v2.controller;

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
import com.algaworks.algafood.api.v2.assembler.CidadeDtoAssemblerV2;
import com.algaworks.algafood.api.v2.controller.openapi.controller.CidadeControllerOpenApiV2;
import com.algaworks.algafood.api.v2.disassembler.CidadeInputDtoDisassemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeDtoV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputDtoV2;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;



@RestController
@RequestMapping(value = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerOpenApiV2  {

	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	CadastroCidadeService cadastroCidade;
	@Autowired
	CidadeInputDtoDisassemblerV2 cidadeInputDtoDisassembler;
	@Autowired
	CidadeDtoAssemblerV2 cidadeDtoAssembler;

	
	@GetMapping
	public CollectionModel<CidadeDtoV2> listar() {
		return cidadeDtoAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@GetMapping("/{cidadeId}")
	public CidadeDtoV2 buscar(@PathVariable("cidadeId") Long id) {
		return  cidadeDtoAssembler.toModel(cadastroCidade.buscarOuFalhar(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDtoV2 criar(@RequestBody @Valid  CidadeInputDtoV2 cidadeInputDtp ) {
		try {
				
			Cidade cidade = cidadeInputDtoDisassembler.cidadeInputDtoToCidade(cidadeInputDtp);
			cadastroCidade.salvar(cidade);
			CidadeDtoV2 cidadeDto = cidadeDtoAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriResponseHeader(cidadeDto.getIdCidade());
			
			return cidadeDto;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeDtoV2 editar(@PathVariable("cidadeId") Long id, @RequestBody @Valid CidadeInputDtoV2 cidadeInputDto) {
		
		try {			
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
			
			cidadeInputDtoDisassembler.copyToDomainObject(cidadeInputDto, cidadeAtual);
			
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
			
			return cidadeDtoAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("cidadeId") Long id) {
		
		cadastroCidade.excluir(id);
	}
}
