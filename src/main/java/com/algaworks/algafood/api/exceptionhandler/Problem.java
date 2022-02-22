package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@JsonInclude(Include.NON_NULL)
@ApiModel("Problema")
public class Problem {
	
	
	@ApiModelProperty(example = "400")
	private Integer status;
	
	@ApiModelProperty(example = "Dados inválidos")
	private String title;	

	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String detail;
	
	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos")
	private String type;
	
	private String instance;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String userMessage;
	
	@ApiModelProperty(example = "2022-02-17T12:27:29.443664Z")
	private OffsetDateTime timestamp;
	
	@ApiModelProperty("Objetos ou campos que geraram o erro")
	private List<Object> objects;
	
	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object{
		
		@ApiModelProperty(example = "nome")
		private String name;
		
		@ApiModelProperty(example = "Nome da cozinha é obrigatório")
		private String userMessage;
	}
	
}
