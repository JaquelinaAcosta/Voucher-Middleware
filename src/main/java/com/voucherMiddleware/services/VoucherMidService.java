package com.voucherMiddleware.services;

import com.voucherMiddleware.model.RequestVoucher;
import com.voucherMiddleware.model.ResponseMiddlewere;
import com.voucherMiddleware.model.VoucherDto;

public interface VoucherMidService {
	
	public RequestVoucher consultarVoucher(VoucherDto voucherDto) throws Exception;
	public ResponseMiddlewere informarVoucher(String request);
	public RequestVoucher procesarVoucherAFacturar(VoucherDto voucherDto) throws Exception;
	public RequestVoucher procesarVoucherUtilizar(VoucherDto voucherDto) throws Exception;

}
