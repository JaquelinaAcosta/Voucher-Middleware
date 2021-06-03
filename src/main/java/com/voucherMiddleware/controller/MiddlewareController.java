package com.voucherMiddleware.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.voucherMiddleware.model.RequestVoucher;
import com.voucherMiddleware.model.ResponseMiddlewere;
import com.voucherMiddleware.model.VoucherDto;
import com.voucherMiddleware.services.VoucherMidService;
import com.voucherMiddleware.services.impl.VoucherMidServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/middlewareVoucher")
@CrossOrigin
public class MiddlewareController {
	
	@Autowired(required=true)
	VoucherMidServiceImpl voucherMidService;
	
	private ObjectMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Controla que los vouchers esten disponibles para ser utilizados (que los estados no sean Vencidos y Utilizados, y el dni corresponda con el codigo de voucher))
	@PostMapping(path = "/enviar/consulta", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> consultarVoucher(@RequestBody String voucherJson) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode objJson = mapper.readTree(voucherJson);
		JsonNode requestvoucherDtoNodo = mapper.readTree(objJson.get("string").asText());
		VoucherDto voucherDto = mapper.convertValue(requestvoucherDtoNodo, VoucherDto.class);

		RequestVoucher request = new RequestVoucher();
		ResponseMiddlewere response = new ResponseMiddlewere();
		ObjectNode newNode = mapper.createObjectNode();
		
		logger.info("contoller middlew ingreso:");
		System.out.println(voucherDto);
		try {
			request = voucherMidService.consultarVoucher(voucherDto);
		} catch (Throwable e) {
			e.printStackTrace();
			response.setStatus("ERROR");
			if(e.getMessage() == null) {
			response.setMsgError("Error al procesar voucher. Verificar DNI y/o código/s de voucher ingresados.");
			}else {
				response.setMsgError(e.getMessage());
			}
			newNode.putPOJO("ResponseMiddlewere", response);
			return new ResponseEntity<String>(newNode.toString(),HttpStatus.OK);
		}
		try {
			response = voucherMidService.informarVoucher(mapper.writeValueAsString(request));
		} catch (Throwable e) {
			e.printStackTrace();
			response.setStatus("ERROR");
			response.setMsgError("Error al informar voucher: " + e.getMessage());
			
		}
		newNode.putPOJO("ResponseMiddlewere", response);
		return new ResponseEntity<String>(newNode.toString(),HttpStatus.OK);
	}
	
	//Usar voucher y cambiar estado "A FACTURAR"
	@PostMapping(path = "/enviar/cambioAFacturar", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> aFacturarVoucher(@RequestBody String voucherDtoJson) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode objJson = mapper.readTree(voucherDtoJson);
		JsonNode requestvoucherDtoNodo = mapper.readTree(objJson.get("string").asText());
		VoucherDto voucherDto = mapper.convertValue(requestvoucherDtoNodo, VoucherDto.class);

		RequestVoucher request = new RequestVoucher();
		ResponseMiddlewere response = new ResponseMiddlewere();
		ObjectNode newNode = mapper.createObjectNode();
		try {
			request = voucherMidService.procesarVoucherAFacturar(voucherDto);
		} catch (Throwable e) {
			e.printStackTrace();
			response.setStatus("ERROR");
			response.setMsgError("Error al procesar voucher: " + e.getMessage());
			newNode.putPOJO("ResponseMiddlewere", response);
			return new ResponseEntity<String>(newNode.toString(),HttpStatus.OK);
		}
		try {
			response = voucherMidService.informarVoucher(mapper.writeValueAsString(request));
		} catch (Throwable e) {
			e.printStackTrace();
			response.setStatus("ERROR");
			response.setMsgError("Error al informar voucher: " + e.getMessage());
			
		}
		newNode.putPOJO("ResponseMiddlewere", response);
		return new ResponseEntity<String>(newNode.toString(),HttpStatus.OK);
	}
	
	
	//Usar voucher completamente, guardar codigo de factura y cambiar estado "UTILIZADO"
	@PostMapping(path = "/enviar/utilizado", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> utilizarVoucher(@RequestBody String voucherDtoJson) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode objJson = mapper.readTree(voucherDtoJson);
		JsonNode requestvoucherDtoNodo = mapper.readTree(objJson.get("string").asText());
		VoucherDto voucherDto = mapper.convertValue(requestvoucherDtoNodo, VoucherDto.class);

		RequestVoucher request = new RequestVoucher();
		ResponseMiddlewere response = new ResponseMiddlewere();
		ObjectNode newNode = mapper.createObjectNode();
		try {
			request = voucherMidService.procesarVoucherUtilizar(voucherDto);
		} catch (Throwable e) {
			e.printStackTrace();
			response.setStatus("ERROR");
			response.setMsgError("Error al procesar voucher:" + e.getMessage());
			newNode.putPOJO("ResponseMiddlewere", response);
			return new ResponseEntity<String>(newNode.toString(),HttpStatus.OK);
		}
		try {
			response = voucherMidService.informarVoucher(mapper.writeValueAsString(request));
		} catch (Throwable e) {
			e.printStackTrace();
			response.setStatus("ERROR");
			response.setMsgError("Error al informar voucher: " + e.getMessage());
			
		}
		newNode.putPOJO("ResponseMiddlewere", response);
		return new ResponseEntity<String>(newNode.toString(),HttpStatus.OK);
	}
	
	
	
	
	//TODO: SACAR ESTO! es solo para simular un string desde rumbo
	@RequestMapping(value = "/string", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)	
public String stringVoucher(@RequestBody @Valid VoucherDto voucherJson) throws Exception {
		
		List<String> codigo = new ArrayList<String>();
		codigo.add("138569");
		codigo.add("138111");
		
		VoucherDto voucherDto = new VoucherDto();
		
		logger.info("voucher json y dto null");
		System.out.println(voucherJson.getDniCliente());
		System.out.println(voucherDto);
		voucherDto = voucherJson;
		voucherDto.setCodigoVoucher(codigo);
		
		logger.info("asignacion a dto null");
		System.out.println(voucherDto);

		String dos = String.valueOf(voucherDto);

		String vouch;
		vouch = voucherDto.toString();
		return vouch;
	}
	
}
