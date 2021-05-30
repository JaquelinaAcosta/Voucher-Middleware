package com.voucherMiddleware.model;

import java.util.List;



public class RequestVoucher {
	
	private Integer cantidadVoucherEnviados;
	private List<?> listaVouchers;
//	private boolean imprimir;
	
	public Integer getCantidadVoucherEnviados() {
		return cantidadVoucherEnviados;
	}
	public void setCantidadVoucherEnviados(Integer cantidadVoucherEnviados) {
		this.cantidadVoucherEnviados = cantidadVoucherEnviados;
	}
	public List<?> getListaVouchers() {
		return listaVouchers;
	}
	public void setListaVouchers(List<?> listaVouchers) {
		this.listaVouchers = listaVouchers;
	}
//	public boolean isImprimir() {
//		return imprimir;
//	}
//	public void setImprimir(boolean imprimir) {
//		this.imprimir = imprimir;
//	}

}
