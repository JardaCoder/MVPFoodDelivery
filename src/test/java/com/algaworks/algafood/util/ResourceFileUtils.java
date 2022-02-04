package com.algaworks.algafood.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import com.algaworks.algafood.util.enums.RequestType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ResourceFileUtils {
	
	
	public static String getJsonFromResourceFile(String jsonName, RequestType type) throws JsonParseException, JsonMappingException, IOException {
		   try {
		        InputStream stream = ResourceUtils.class.getResourceAsStream(type.getBasePath() + jsonName);
		        String json = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		        return json;
		    } catch (IOException e) {
		        throw new RuntimeException(e);
		    }
	}

}
