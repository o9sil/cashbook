package com.gdu.cashbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @componentScan
@SpringBootApplication
public class CashbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}

}
