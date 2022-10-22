package br.com.outlier.rascunhospringrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RascunhoSpringRestAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(RascunhoSpringRestAPIApplication.class, args);
	}
}
