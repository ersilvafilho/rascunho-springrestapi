package br.com.outlier.rascunhospringrestapi.form;

import javax.validation.constraints.NotBlank;

import br.com.outlier.rascunhospringrestapi.model.entity.Topico;
import br.com.outlier.rascunhospringrestapi.repository.TopicoRepository;
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
public class TopicoUpdateForm {

	@NotBlank
	private String titulo;
	@NotBlank
	private String mensagem;

	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.getReferenceById(id);
		topico.setTitulo(titulo);
		topico.setMensagem(mensagem);
		return topico;
	}

}
