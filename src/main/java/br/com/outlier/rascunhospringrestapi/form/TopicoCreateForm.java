package br.com.outlier.rascunhospringrestapi.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.outlier.rascunhospringrestapi.model.entity.Topico;
import br.com.outlier.rascunhospringrestapi.repository.CursoRepository;
import br.com.outlier.rascunhospringrestapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicoCreateForm {

	@NotBlank
	private String titulo;
	@NotBlank
	private String mensagem;
	@NotNull
	private Long autorId;
	@NotNull
	private Long cursoId;

	public Topico toTopico(UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
		return Topico.builder().titulo(titulo).mensagem(mensagem).autor(usuarioRepository.getReferenceById(autorId)).curso(cursoRepository.getReferenceById(cursoId)).build();
	}
}
