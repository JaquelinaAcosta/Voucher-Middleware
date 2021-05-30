package com.voucherMiddleware.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//esta clase intercepta las peticiones enviadas usando un RestTemplate agregandole 
//el token de autenticacion
public class OAuthTokenInterceptor implements ClientHttpRequestInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(OAuthTokenInterceptor.class);

	private String url;
	private String idClient;
	private String secretClient;
	private String username;
	private String password;
	private final RestTemplate simpleRestTemplate = new RestTemplate();
	private OAuth2AccessToken tokenCache;

	public OAuthTokenInterceptor(String url, String idClient, String secretClient, 
															String username, String password) {
		this.url = url;
		this.idClient = idClient;
		this.secretClient = secretClient;
		this.username = username;
		this.password = password;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
			 OAuth2AccessToken token = getToken();
		request.getHeaders().add("Authorization", "Bearer " + token);
		
		return execution.execute(request, body);
	}
	
	private OAuth2AccessToken getToken() {
		if (tokenCache == null || tokenCache.isExpired()) {
			log.info("Solicitando nuevo token. Url: " + url);
			tokenCache = loginWithCredentials();
		}else{
			log.info("Token sigue siendo valido por {} segundos. Url: " + url, tokenCache.getExpiresIn());
		}

		return tokenCache;
	}
	
	
	private OAuth2AccessToken loginWithCredentials() {
		// armo la cabecesa de la llamada (servidor de autenticacion)
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(idClient, secretClient);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username", username);
		map.add("password", password);
		map.add("grant_type", "password");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		
		return simpleRestTemplate.postForObject(url, request, OAuth2AccessToken.class);
	}
}