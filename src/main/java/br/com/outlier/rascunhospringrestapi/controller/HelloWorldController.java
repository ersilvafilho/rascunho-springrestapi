package br.com.outlier.rascunhospringrestapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public String helloWorld(@RequestParam(required = false) String nome) {
		return "Bem vindo! " + nome;
	}

}
