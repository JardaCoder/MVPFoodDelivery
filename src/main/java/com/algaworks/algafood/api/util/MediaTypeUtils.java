package com.algaworks.algafood.api.util;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;


/**
 *	
 * 
 * @author Jardel Schaefer
 * @since 14/02/2022
 */
@Component
public class MediaTypeUtils {

	
	public static void verificarCompatibilidadesMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch( mediaType -> mediaType.isCompatibleWith(mediaTypeFoto));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}
}
