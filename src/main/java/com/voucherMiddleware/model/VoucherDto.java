package com.voucherMiddleware.model;

import java.util.List;

public class VoucherDto {
	
	private List<String> codigoVoucher;
	private String dniCliente;
	private String facturaAsociada;
	
	public VoucherDto() {
		
	}
	public VoucherDto(List<String> codigoVoucher, String dniCliente, String facturaAsociada) {
		super();
		this.codigoVoucher = codigoVoucher;
		this.dniCliente = dniCliente;
		this.facturaAsociada = facturaAsociada;
	}
	public List<String> getCodigoVoucher() {
		return codigoVoucher;
	}
	public void setCodigoVoucher(List<String> codigoVoucher) {
		this.codigoVoucher = codigoVoucher;
	}
	public String getDniCliente() {
		return dniCliente;
	}
	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}
	public String getFacturaAsociada() {
		return facturaAsociada;
	}
	public void setFacturaAsociada(String facturaAsociada) {
		this.facturaAsociada = facturaAsociada;
	}

	@Override
	  public String toString() {
	    return "{ codigoVouche: " + codigoVoucher + ", dniCliente: " + dniCliente + ", facturaAsociada: " + facturaAsociada  + "}";
	  }
}
