package br.com.outlier.rascunhospringrestapi.dto;

import org.springframework.beans.BeanUtils;

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
public class UsuarioDTO {

	private Long id;
	private String nome;
	private String email;
	private String senha;

	public UsuarioDTO(Usuario usuario) {
		BeanUtils.copyProperties(usuario, this);
	}
}
