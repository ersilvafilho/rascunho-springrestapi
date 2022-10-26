package br.com.outlier.rascunhospringrestapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.outlier.rascunhospringrestapi.model.entity.Usuario;
import br.com.outlier.rascunhospringrestapi.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends GenericController<Usuario, Long, UsuarioRepository> {

	@GetMapping("/nome")
	public ResponseEntity<Usuario> findUsuarioByEmail(@RequestParam String email) {
		return ResponseEntity.ok(repository.findByEmail(email));
	}

}
