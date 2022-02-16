package com.algaworks.algafood.client;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientConfig implements ClientHttpRequestInterceptor {
	
	
    private final String headerName;

    private final String headerValue;

    public ClientConfig(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
        request.getHeaders().set("Accept", MediaType.APPLICATION_JSON.toString());
        return execution.execute(request, body);
	}

}
