package br.com.outlier.rascunhospringrestapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.outlier.rascunhospringrestapi.form.LoginForm;
import br.com.outlier.rascunhospringrestapi.util.TokenUtil;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtil tokenUtil;

	@PostMapping
	public ResponseEntity<Map<String, Object>> autenticar(@RequestBody LoginForm loginForm) {
		UsernamePasswordAuthenticationToken token = loginForm.toToken();
		try {
			Authentication auth = authenticationManager.authenticate(token);
			return ResponseEntity.ok(
					Map.of("responseContent", Map.of("mensagem", "Bem vindo!", "token", tokenUtil.gerarToken(auth), "tipoAutenticacao", "Bearer")));

		} catch (AuthenticationException authenticationException) {
			return ResponseEntity.badRequest().body(Map.of("responseContent", Map.of("mensagem", authenticationException.getMessage())));
		}
	}
}
