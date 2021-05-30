package com.voucherMiddleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan({"com.voucherMiddleware","com.voucherExcel"} )
@EnableTransactionManagement
public class VoucherMiddlewareApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoucherMiddlewareApplication.class, args);
	}

}
