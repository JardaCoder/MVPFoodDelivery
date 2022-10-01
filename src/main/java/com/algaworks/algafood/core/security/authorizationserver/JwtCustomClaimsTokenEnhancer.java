package com.algaworks.algafood.core.security.authorizationserver;

import java.util.HashMap;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;


public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		if(authentication.getPrincipal() instanceof AuthUser) {
			var authUser  =  (AuthUser) authentication.getPrincipal();
			var informations = new HashMap<String, Object>();
			
			informations.put("nome_completo",authUser.getFullName());
			informations.put("usuario_id", authUser.getUserId());
			
			var oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
			
			oAuth2AccessToken.setAdditionalInformation(informations);
		}
		
		return accessToken;
	}

}
