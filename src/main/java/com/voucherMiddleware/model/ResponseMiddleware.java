package com.voucherMiddleware.model;

public class ResponseMiddleware {
    
	private String status;
	private String generacion;
	private String msgError;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getGeneracion() {
		return generacion;
	}
	
	public void setGeneracion(String generacion) {
		this.generacion = generacion;
	}
	
	public String getMsgError() {
		return msgError;
	}
	
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

}
