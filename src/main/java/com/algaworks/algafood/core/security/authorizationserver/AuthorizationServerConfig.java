package com.algaworks.algafood.core.security.authorizationserver;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JpaUserDetailsService jpaUserDetailsService;
	
	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;
	
	@Autowired
	private DataSource dataSource;
	
//	@Autowired
//	private RedisConnectionFactory redisConnectionFactory;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
//		.inMemory()
//			.withClient("jardafood-web")
//			.secret(passwordEncoder.encode("web123"))
//			.authorizedGrantTypes("password", "refresh_token")
//			.scopes("WRITE", "READ")
//			.accessTokenValiditySeconds(6 * 60 * 60)// 6 horas
//			.refreshTokenValiditySeconds(60 * 24 * 60 * 10) // 10 dias
//		.and()
//			.withClient("foodanalytics")
//			.secret(passwordEncoder.encode(""))
//			.authorizedGrantTypes("authorization_code")
//			.scopes("WRITE", "READ")
//			.redirectUris("http://aplicacao-cliente")
//		.and()
//			.withClient("webadmin")
//			.secret(passwordEncoder.encode("implicit"))
//			.authorizedGrantTypes("implicit")
//			.scopes("WRITE", "READ")
//			.redirectUris("http://aplicacao-cliente-implicit")
//		.and()
//			.withClient("faturamento")
//			.secret(passwordEncoder.encode("faturamento123"))
//			.authorizedGrantTypes("client_credentials")
//			.scopes("WRITE", "READ")
//		.and()
//			.withClient("checktoken")
//				.secret(passwordEncoder.encode("check123"));
		
			
		
// other clients
//		.and()
//			.withClient("jardafood-mobile")
//			.secret(passwordEncoder.encode("mobile123"))
//			.authorizedGrantTypes("password")
//			.scopes("write", "read");
	}
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		var enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));
		
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(jpaUserDetailsService)
			.reuseRefreshTokens(false)
			.accessTokenConverter(jwtAccessTokenConverter())
			.tokenEnhancer(enhancerChain)
			//Sempre apos token store
			.approvalStore(approvalStore(endpoints.getTokenStore()))
//			.tokenStore(redisTokenStore())
			.tokenGranter(tokenGranter(endpoints));
		
	}
	
	private ApprovalStore approvalStore(TokenStore tokenStore) {
		var approvalStore = new TokenApprovalStore();
		approvalStore.setTokenStore(tokenStore);
		
		return approvalStore;
	}
	
	@Bean
	public JWKSet jwtSet() {
		RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
				.keyUse(KeyUse.SIGNATURE)
				.algorithm(JWSAlgorithm.RS256)
				.keyID("jardafood-key-id");
		
		return new JWKSet(builder.build());
 	}
	
//	private TokenStore redisTokenStore() {
//		return new RedisTokenStore(redisConnectionFactory);
//	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		// Chave simétrica.
		//		jwtAccessTokenConverter.setSigningKey("89a7sd89f7as98f7dsa98fds7fd89sasd9898asdf98s");
		
		jwtAccessTokenConverter.setKeyPair(keyPair());
		
		return jwtAccessTokenConverter;
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("permitAll()")
			.tokenKeyAccess("permitAll()")
			.allowFormAuthenticationForClients();
	}
	
	private KeyPair keyPair() {
		//Chave Assinmétrica.
		var jksResource = jwtKeyStoreProperties.getJksLocation();
		var jksStorePass = jwtKeyStoreProperties.getPassword().toCharArray();
		var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();
		
		var keyStoreKeyFactory =  new KeyStoreKeyFactory(jksResource, jksStorePass);
		
		return keyStoreKeyFactory.getKeyPair(keyPairAlias);
		
	}
	
	
	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
}
