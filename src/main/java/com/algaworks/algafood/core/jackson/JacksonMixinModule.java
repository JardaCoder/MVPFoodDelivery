package com.algaworks.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 
 * @author Jardel Schaefer
 * Classe de exemplo para configuração de Mixins.
 *
 */
@Component
public class JacksonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
//		setMixInAnnotation(Estado.class, EstadoMixin.class);
//		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}
}
