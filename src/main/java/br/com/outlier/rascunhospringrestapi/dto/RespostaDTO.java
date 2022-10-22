package br.com.outlier.rascunhospringrestapi.dto;

import java.time.LocalDateTime;

import javax.persistence.ManyToOne;

import org.springframework.beans.BeanUtils;

import br.com.outlier.rascunhospringrestapi.model.entity.Resposta;
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
public class RespostaDTO {

	private Long id;
	private String mensagem;
	@ManyToOne
	private TopicoDTO topico;
	@Builder.Default
	private LocalDateTime dataCriacao = LocalDateTime.now();
	@ManyToOne
	private UsuarioDTO autor;
	@Builder.Default
	private Boolean solucao = false;

	public RespostaDTO(Resposta resposta) {
		BeanUtils.copyProperties(resposta, this);
	}
}
