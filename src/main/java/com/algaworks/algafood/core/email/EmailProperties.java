package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("jardafood.email")
public class EmailProperties {

	@NotNull
	private String remetente;
	
	private String emailSandbox;
	
	private TipoEmail tipo = TipoEmail.FAKE;
	
	
	public enum TipoEmail{
		FAKE, SMTP, SANDBOX
	}
}
