package br.com.outlier.rascunhospringrestapi.dto;

import org.springframework.beans.BeanUtils;

import br.com.outlier.rascunhospringrestapi.model.entity.Curso;
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
public class CursoDTO {

	private Long id;
	private String nome;
	private String categoria;

	public CursoDTO(Curso curso) {
		BeanUtils.copyProperties(curso, this);
	}
}
