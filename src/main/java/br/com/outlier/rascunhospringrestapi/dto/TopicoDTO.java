package br.com.outlier.rascunhospringrestapi.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import br.com.outlier.rascunhospringrestapi.model.entity.StatusTopico;
import br.com.outlier.rascunhospringrestapi.model.entity.Topico;
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
public class TopicoDTO {

	private Long id;
	@NotBlank
	private String titulo;
	@NotBlank
	private String mensagem;
	@Builder.Default
	private LocalDateTime dataCriacao = LocalDateTime.now();
	@Builder.Default
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;
	@NotNull
	private UsuarioDTO autor;
	@NotNull
	private CursoDTO curso;
	@Builder.Default
	private List<RespostaDTO> respostas = new ArrayList<>();

	public TopicoDTO(Topico topico) {
		BeanUtils.copyProperties(topico, this);
		this.autor = new UsuarioDTO();
		this.curso = new CursoDTO();
		BeanUtils.copyProperties(topico.getAutor(), this.autor);
		BeanUtils.copyProperties(topico.getCurso(), this.curso);
	}
}
