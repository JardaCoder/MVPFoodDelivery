package com.algaworks.algafood.util.enums;

import lombok.Getter;

@Getter
public enum RequestType {

	SUCCESS("/jsons/success/"),
	FAIL("/jsons/fails/");
	
	private String basePath;

	private RequestType(String basePath) {
		this.basePath = basePath;
	}
	
	
}
