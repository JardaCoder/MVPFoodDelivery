package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDtoAssembler;
import com.algaworks.algafood.api.controller.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.api.disassembler.PedidoInputDtoDisassembler;
import com.algaworks.algafood.api.model.PedidoDto;
import com.algaworks.algafood.api.model.PedidoResumoDto;
import com.algaworks.algafood.api.model.input.PedidoInputDto;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {
	
	
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	EmissaoPedidoService cadastroPedido;
	@Autowired
	private PedidoDtoAssembler pedidoDtoAssembler;
	@Autowired
	private PedidoResumoDtoAssembler pedidoResumoDtoAssembler;
	@Autowired
	private PedidoInputDtoDisassembler pedidoInputDtoDisassembler;

	
	@GetMapping
	public Page<PedidoResumoDto> pesquisar(PedidoFilter pedidoFilter, @PageableDefault(size = 10)  Pageable pageable){
		
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);
		
		List<PedidoResumoDto> pedidosDto = pedidoResumoDtoAssembler.pedidoResumosToListPedidoResumoDto(pedidosPage.getContent());
		
		Page<PedidoResumoDto> pedidosDtoPage = new PageImpl<PedidoResumoDto>(pedidosDto, pageable, pedidosPage.getTotalElements());
		
		return pedidosDtoPage;
	}
	

	@GetMapping("/{codigoPedido}")
	public PedidoDto buscar(@PathVariable("codigoPedido") String codigoPedido){
		return pedidoDtoAssembler.pedidoToPedidoDto(cadastroPedido.buscarOuFalhar(codigoPedido));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDto criar(@RequestBody @Valid PedidoInputDto pedidoInputDto){
		
		try {
			var cliente = new Usuario();
			
			cliente.setId(1L);
			
			Pedido pedido = pedidoInputDtoDisassembler.pedidoInputDtoToPedido(pedidoInputDto);
			
			pedido.setCliente(cliente);
			
			pedido = cadastroPedido.processarPedido(pedido);
			
			return pedidoDtoAssembler.pedidoToPedidoDto(pedido);
			
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		
		var mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"restaurante.nome", "restaurante.nome",
				"restaurante.id", "restaurante.id",
				"cliente.id", "cliente.id",
				"cliente.nome", "cliente.nome"
				);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
	
}

//@GetMapping
//public MappingJacksonValue listar(@RequestParam(required = false) String campos){
//	List<Pedido> pedidos = pedidoRepository.findAll();
//	List<PedidoResumoDto> pedidosDto = pedidoResumoDtoAssembler.pedidoResumosToListPedidoResumoDto(pedidos);
//	
//	MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDto);
//	
//	SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//	
//	filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//	
//	if(StringUtils.hasLength(campos)) {
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//	}
//	
//	pedidosWrapper.setFilters(filterProvider);
//	
//	return pedidosWrapper;
//}	
