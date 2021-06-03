package com.voucherMiddleware.services.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.voucherExcel.model.Voucher;
import com.voucherExcel.repository.VoucherRepository;
import com.voucherMiddleware.model.RequestVoucher;
import com.voucherMiddleware.model.ResponseMiddlewere;
import com.voucherMiddleware.model.VoucherDto;

@Service
public class VoucherMidServiceImpl{
	
//	@Value("${serviciosexternos.url.comprobanteservices.enviarcomprobante}")
//	private String ENVIAR_COMPROBANTE_DIGITAL_ENDPOINT;
	
	
	//TODO: VER COMO CONECTO A RUMBO
//	@Value("${serviciosexternos.url.voucherservices.enviarvoucher}")
//	private String ENVIAR_VOUCHER_ENDPOINT;
	
	@Autowired
	private VoucherRepository voucherRepository;
	
//	@Autowired
//	RestTemplate restTemplateVoucher;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy");
	
	
	//verificar si se puede usar el voucher
	public RequestVoucher consultarVoucher(VoucherDto voucherDto) throws Exception {
		
		RequestVoucher request = new RequestVoucher();
		List<Voucher> vouchersMid = new ArrayList<Voucher>();
		
		request.setCantidadVoucherEnviados(voucherDto.getCodigoVoucher().size());
		
		for (String cv : voucherDto.getCodigoVoucher()) {
			
			Voucher voucher = new Voucher();
			voucher = voucherRepository.findByCodigoVoucherAndDni(cv, voucherDto.getDniCliente());
			System.out.println(voucher);
			System.out.println(voucher.getEstado());
			System.out.println("valor de la habilitacuin del voucher");
			System.out.println(voucher.getHabilitado().booleanValue());
			System.out.println(voucher.getHabilitado());
					
			try {
				if ((voucher.getEstado().equals("E") || voucher.getEstado().equals("AF")) && voucher.getHabilitado() == true) {
					vouchersMid.add(voucher);
				}
				else if (voucher.getHabilitado() == false) {
					throw new NullPointerException("El voucher: " + voucher.getCodigoVoucher() + " no se encuentra habilitado"); 
				}
				 switch(voucher.getEstado()) {
			        case "U":
			        	throw new NullPointerException("El voucher: " + voucher.getCodigoVoucher() + " se encuentra UTILIZADO");
			        case "ND":
			        	throw new NullPointerException("El voucher: " + voucher.getCodigoVoucher() + " se encuentra NO DISPONIBLE, verificar si existe duplicado");
			        case "V":
			        	throw new NullPointerException("El voucher: " + voucher.getCodigoVoucher() + " se encuentra VENCIDO");
			        default:
			        	break;
			        }

		   } catch (Exception e) {
			    throw new RuntimeException("Error: " + e.getMessage());
		   }	
			
		}
		request.setListaVouchers(vouchersMid);
		return request;
	}
	
	// enviar Respuesta
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseMiddlewere informarVoucher(String request) {
		
		ResponseMiddlewere responseMiddlewere = new ResponseMiddlewere();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> respuestaBridge = new HttpEntity<String>(request, headers);
		
		//VER ESTOOOO
//		ResponseEntity<HashMap> responseEntity = restTemplateVoucher
//				.exchange(ENVIAR_VOUCHER_ENDPOINT, HttpMethod.POST, respuestaBridge, HashMap.class);
//		ResponseEntity<HashMap> responseEntity = restTemplateVoucher
//				.exchange(null, HttpMethod.POST, respuestaBridge, HashMap.class);
		
//		ResponseEntity<HashMap> responseEntity = restTemplateVoucher
//				.exchange(null, HttpMethod.POST, respuestaBridge, HashMap.class);

//		if (responseEntity.getHeaders().getFirst("RESULTADO").equals("ERROR")) {
//			responseMiddlewere.setStatus("ERROR");
//			responseMiddlewere.setMsgError("Error al informar la operacion");
//			return responseMiddlewere;
//		}

//		HashMap<String, String> mapaRespuesta = responseEntity.getBody();
		HashMap<String, String> mapaRespuesta = new HashMap<>();

		responseMiddlewere.setStatus("OK");
		responseMiddlewere.setGeneracion(mapaRespuesta.get("Disponible"));
		if(mapaRespuesta.get("MsgError") != null) {
			responseMiddlewere.setMsgError(mapaRespuesta.get("MsgError"));
		}

		return responseMiddlewere;	
	
	}
	
	
	//Cambiar a estado a Facturar
public RequestVoucher procesarVoucherAFacturar(VoucherDto voucherDto) throws Exception {
		
		RequestVoucher request = new RequestVoucher();
		List<Voucher> vouchersMid = new ArrayList<Voucher>();
		
		request.setCantidadVoucherEnviados(voucherDto.getCodigoVoucher().size());
		
		for (String cv : voucherDto.getCodigoVoucher()) {
			
			Voucher voucher = new Voucher();
			voucher = voucherRepository.findByCodigoVoucherAndDni(cv, voucherDto.getDniCliente());
			try {
				if (voucher.getEstado().equals("E") || voucher.getEstado().equals("AF")) {
					voucher.setEstadosPasados(voucher.getEstadosPasados()+'\n'+"A FACTURAR, el dia "+ f2.format(new Date())+" por el usuario: "+"agregar usuario");
					voucher.setEstado("AF");
					voucherRepository.save(voucher);
					vouchersMid.add(voucher);
				//	voucherRepository.save(voucher);
				}
		     
		   } catch (Exception e) {
			    throw new RuntimeException("Falla en la busqueda de voucher: " + e.getMessage());
		   }	
			
		}
		request.setListaVouchers(vouchersMid);
		return request;
	}

//Cambiar a estado a Utilizado y guardar codigo de factura asociada
public RequestVoucher procesarVoucherUtilizar(VoucherDto voucherDto) throws Exception {
	
	RequestVoucher request = new RequestVoucher();
	List<Voucher> vouchersMid = new ArrayList<Voucher>();
	
	request.setCantidadVoucherEnviados(voucherDto.getCodigoVoucher().size());
	
	for (String cv : voucherDto.getCodigoVoucher()) {
		
		Voucher voucher = new Voucher();
		voucher = voucherRepository.findByCodigoVoucherAndDni(cv, voucherDto.getDniCliente());
		try {
			if (voucher.getEstado().equals("AF")) {
				voucher.setEstadosPasados(voucher.getEstadosPasados()+'\n'+"UTILIZADO, el dia "+ f2.format(new Date())+" por el usuario: "+"agregar usuario");
				voucher.setEstado("U");
				voucher.setFacturaAsociada(voucherDto.getFacturaAsociada());
				voucherRepository.save(voucher);
				vouchersMid.add(voucher);
				//voucherRepository.save(voucher);
			}
	     
	   } catch (Exception e) {
		    throw new RuntimeException("Falla en la busqueda de voucher: " + e.getMessage());
	   }	
		
	}
	request.setListaVouchers(vouchersMid);
	return request;
}

}
