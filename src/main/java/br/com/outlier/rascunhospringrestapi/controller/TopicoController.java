package br.com.outlier.rascunhospringrestapi.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.outlier.rascunhospringrestapi.dto.TopicoDTO;
import br.com.outlier.rascunhospringrestapi.form.TopicoCreateForm;
import br.com.outlier.rascunhospringrestapi.form.TopicoQueryForm;
import br.com.outlier.rascunhospringrestapi.form.TopicoUpdateForm;
import br.com.outlier.rascunhospringrestapi.model.entity.Topico;
import br.com.outlier.rascunhospringrestapi.repository.CursoRepository;
import br.com.outlier.rascunhospringrestapi.repository.TopicoRepository;
import br.com.outlier.rascunhospringrestapi.repository.UsuarioRepository;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public ResponseEntity<Map<String, Object>> listar(@RequestBody(required = false) TopicoQueryForm topicoQueryForm) {
		List<TopicoDTO> lista = null;
		if (topicoQueryForm == null) {
			lista = topicoRepository.findAll().stream().map(TopicoDTO::new).toList();
		} else {
			lista = topicoRepository.findAll(Example.of(topicoQueryForm.toTopico())).stream().map(TopicoDTO::new).toList();
		}
		return ResponseEntity.ok(Map.of("responseContent", Map.of("topicos", lista)));
	}

	@Transactional
	@PostMapping
	public ResponseEntity<Map<String, Object>> cadastrar(@RequestBody @Valid TopicoCreateForm topicoForm, UriComponentsBuilder uriComponentsBuilder) {
		TopicoDTO dto = new TopicoDTO(topicoRepository.save(topicoForm.toTopico(usuarioRepository, cursoRepository)));
		return ResponseEntity.created(uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(dto.getId()).toUri()).body(Map.of("responseContent", Map.of("topico", dto)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> detalhar(@PathVariable("id") Long id) {
		Topico topico = topicoRepository.getReferenceById(id);
		return ResponseEntity.ok(Map.of("responseContent", new TopicoDTO(topico)));
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoUpdateForm topicoForm) {
		Topico topico = topicoForm.atualizar(id, topicoRepository);
		topicoRepository.save(topico);
		return ResponseEntity.ok(Map.of("responseContent", Map.of("mensagem", "Operação realizada com sucesso!")));
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> remover(@PathVariable Long id) {
		topicoRepository.deleteById(id);
		return ResponseEntity.ok(Map.of("responseContent", Map.of("mensagem", "Operação realizada com sucesso!")));
	}
}
