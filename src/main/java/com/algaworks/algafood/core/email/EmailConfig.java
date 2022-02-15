package com.algaworks.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.EnvioEmailFakeService;
import com.algaworks.algafood.infrastructure.service.email.EnvioEmailSandboxService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;
	
	
	@Bean
	public EnvioEmailService envioEmailService() {
		
		EnvioEmailService envioEmailService = null;
		
		switch (emailProperties.getTipo()) {
		case SMTP:
			envioEmailService = new SmtpEnvioEmailService();
			break;
		case SANDBOX:
			envioEmailService = new EnvioEmailSandboxService();
			break;

		default:
			envioEmailService = new EnvioEmailFakeService();
			break;
		}
		
		return envioEmailService;
	}
	
}