package com.algaworks.algafood.api.v1.model;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;


/**
 * Classe de exemplo para retorno em XML.
 * 
 * @author Jardel
 * @since 2022-02-04
 *
 */
@Data
@JacksonXmlRootElement(localName = "cozinhas")
public class CozinhasRepresentation {

	@NonNull
	@JsonProperty("cozinha")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Cozinha> cozinhas;
}
