package br.com.itau.ensurancequote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EnsuranceQuoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnsuranceQuoteApplication.class, args);
	}

}
