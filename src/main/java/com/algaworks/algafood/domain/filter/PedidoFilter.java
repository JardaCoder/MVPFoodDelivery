package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFilter {
	
	
	@ApiModelProperty(example = "1", value = "Id do cliente vinculado ao pedido")
	private Long clienteId;
	
	@ApiModelProperty(example = "1", value = "Id do restaurante vinculado ao pedido")
	private Long restauranteId;
	
	
	@ApiModelProperty(example = "2022-02-11T14:13:25Z", value = "Data de criação mínima do pedido")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;
	
	@ApiModelProperty(example = "2022-02-11T14:13:25Z", value = "Data de criação máxima do pedido")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;

}
