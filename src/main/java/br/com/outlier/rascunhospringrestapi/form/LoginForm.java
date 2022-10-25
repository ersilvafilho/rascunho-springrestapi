package br.com.outlier.rascunhospringrestapi.form;

import javax.validation.constraints.NotBlank;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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
public class LoginForm {

	@NotBlank
	private String email;
	@NotBlank
	private String senha;

	public UsernamePasswordAuthenticationToken toToken() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
}
