package br.com.outlier.rascunhospringrestapi.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	@Cacheable(value = "topicos.listar")
	public ResponseEntity<Map<String, Object>> listar(@RequestBody(required = false) TopicoQueryForm topicoQueryForm,
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 5) Pageable page) {
		List<TopicoDTO> lista = null;
		if (topicoQueryForm == null) {
			lista = topicoRepository.findAll(page).stream().map(TopicoDTO::new).toList();
		} else {
			lista = topicoRepository.findAll(Example.of(topicoQueryForm.toTopico()), page).stream().map(TopicoDTO::new).toList();
		}
		return ResponseEntity.ok(Map.of("responseContent", Map.of("topicos", lista)));
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "topicos.listar", allEntries = true)
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
	@CacheEvict(value = "topicos.listar", allEntries = true)
	public ResponseEntity<Map<String, Object>> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoUpdateForm topicoForm) {
		Topico topico = topicoForm.atualizar(id, topicoRepository);
		topicoRepository.save(topico);
		return ResponseEntity.ok(Map.of("responseContent", Map.of("mensagem", "Operação realizada com sucesso!")));
	}

	@Transactional
	@DeleteMapping("/{id}")
	@CacheEvict(value = "topicos.listar", allEntries = true)
	public ResponseEntity<Map<String, Object>> remover(@PathVariable Long id) {
		topicoRepository.deleteById(id);
		return ResponseEntity.ok(Map.of("responseContent", Map.of("mensagem", "Operação realizada com sucesso!")));
	}
}
