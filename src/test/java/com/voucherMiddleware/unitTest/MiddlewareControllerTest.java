package com.voucherMiddleware.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.voucherExcel.model.Voucher;
import com.voucherExcel.repository.VoucherRepository;
import com.voucherMiddleware.controller.MiddlewareController;
import com.voucherMiddleware.model.RequestVoucher;
import com.voucherMiddleware.model.ResponseMiddleware;
import com.voucherMiddleware.model.VoucherDto;
import com.voucherMiddleware.services.impl.VoucherMidServiceImpl;

class MiddlewareControllerTest {

	@Autowired
	Optional<VoucherDto> voucherDto;
	
	@Autowired
	ResponseMiddleware responseMiddleware;
	
	@Autowired
	RequestVoucher requestVoucher;
	
	@Autowired
	Voucher voucher;
	
	@Autowired
	VoucherMidServiceImpl voucherMidService;
	
	private ObjectMapper mapper;
	
	VoucherRepository voucherRepositoryMock = Mockito.mock(VoucherRepository.class);
	
	@Autowired
	MiddlewareController middlewareController = new MiddlewareController();
	
	@Autowired
	private MockMvc mockMvc;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy");
	
	@BeforeEach
	void setUp() throws Exception {
		
		
		Voucher mockVoucher = voucher();
	
		Mockito.when(voucherRepositoryMock.findByCodigoVoucherAndDni("138569", "27890663")).thenReturn(mockVoucher);
		
		System.out.println(mockVoucher);
		
//		ObjectNode newNode = mapper.createObjectNode();
//		newNode.putPOJO("ResponseMiddlewere", mockResponseMid);
		//Mockito.when(middlewareController.consultarVoucher(voucherJson)).thenReturn(new ResponseEntity<String>(newNode.toString(),HttpStatus.OK));
	}
	@Test
	void consultarVoucher() throws Exception {
	//	fail("Not yet implemented");
		
		
		Gson g = new Gson();
		
		String voucherJson ="\"string\""+":"+"{ \"codigoVoucher\": [\"138569\"], \"dniCliente\": \"27890663\"}";
		System.out.println(g.toJson(voucherJson));
		System.out.println("Hola");
		
//		
//		ResponseMiddleware mockResponseMid = new ResponseMiddleware();
//		mockResponseMid.setStatus("ERROR");
//		mockResponseMid.setGeneracion(null);
//		mockResponseMid.setMsgError("Error: El voucher: 138569 se encuentra UTILIZADO");
//		
//		String mockRes;
//		mockRes = mockResponseMid.toString();
//		//System.out.println(middlewareController.consultarVoucher(voucherJson));
//		System.out.println(mockResponseMid.toString());
//		
//		mockMvc.perform(post("/topsecret").contentType(MediaType.APPLICATION_JSON)
//				.content(asJsonString(sateliteRequest)));
		
	//	ObjectNode newNode = mapper.createObjectNode();
		//newNode.putPOJO("ResponseMiddlewere", mockResponseMid);
		Mockito.when(middlewareController.consultarVoucher(g.toJson(voucherJson))).thenReturn(new ResponseEntity<String>(respMid().toString(), HttpStatus.OK));
	
	}
	
	private Voucher voucher() {
		Voucher mockVoucher = new Voucher();
		mockVoucher.setCodigoVoucher("138569");
		mockVoucher.setHabilitado(true);
		mockVoucher.setEstado("U");
		mockVoucher.setDni("27890663");
		
		return mockVoucher;
	}
	
	private ResponseMiddleware respMid() {
		ResponseMiddleware mockResponseMid = new ResponseMiddleware();
		mockResponseMid.setStatus("ERROR");
		mockResponseMid.setGeneracion(null);
		mockResponseMid.setMsgError("Error: El voucher: 138569 se encuentra UTILIZADO");
		return mockResponseMid;
	}

}
