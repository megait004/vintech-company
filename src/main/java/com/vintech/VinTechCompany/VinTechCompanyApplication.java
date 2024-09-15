package com.vintech.VinTechCompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class VinTechCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinTechCompanyApplication.class, args);
	}

}
