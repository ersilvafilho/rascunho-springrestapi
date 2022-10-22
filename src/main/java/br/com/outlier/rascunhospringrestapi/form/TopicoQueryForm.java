package br.com.outlier.rascunhospringrestapi.form;

import br.com.outlier.rascunhospringrestapi.model.entity.Curso;
import br.com.outlier.rascunhospringrestapi.model.entity.Topico;
import br.com.outlier.rascunhospringrestapi.model.entity.Usuario;
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
public class TopicoQueryForm {

	private String titulo;
	private String mensagem;
	private Long autorId;
	private Long cursoId;

	public Topico toTopico() {
		Usuario autor = Usuario.builder().id(autorId).build();
		Curso curso = Curso.builder().id(cursoId).build();
		Topico topico = new Topico(null, titulo, mensagem, null, null, autor, curso, null);
		return topico;
	}
}
