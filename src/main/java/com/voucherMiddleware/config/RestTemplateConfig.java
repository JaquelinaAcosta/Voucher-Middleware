package com.voucherMiddleware.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.voucherMiddleware.repository.UserMsRepository;

//import com.carsa.comprobante.electronico.mid.repository.UserMsRepository;
//import com.comprobante.digital.domain.UserMs;

@Configuration
public class RestTemplateConfig {

//	@Value("${serviciosexternos.url.vouhcer.auth}")
//	private String AUTH_ENDPOINT_VOUCHER;
//
//	@Autowired
//	UserMsRepository userMsRepository;
//
//	@Bean //bean generico para llamadas a cualquier MS
//	public RestTemplate restTemplate() {  
//		return new RestTemplate();
//	}
//
//	@Bean //bean especifico para llamadas al MS comprobante-digital
//	public RestTemplate restTemplateComprobanteDigital() {
//		RestTemplate restTemplate = new RestTemplate();
//
//		UserMs userMs = userMsRepository.findByUsername("VOUCHER");
//
//		OAuthTokenInterceptor oAuthTokenInterceptor = new OAuthTokenInterceptor(AUTH_ENDPOINT_VOUCHER,
//				userMs.getClient_id(), userMs.getClient_secret(), userMs.getUsername(), userMs.getPasswordNotEncoded());
//		// agregamos un interceptor que se encarga de agregar el token para lograr
//		// la autenticacion contra el servicio que se llama
//		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
//		interceptors.add(oAuthTokenInterceptor);
//		restTemplate.setInterceptors(interceptors);
//		return restTemplate;
//	}

}
