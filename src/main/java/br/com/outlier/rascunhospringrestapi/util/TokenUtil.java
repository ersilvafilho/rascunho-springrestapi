package br.com.outlier.rascunhospringrestapi.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.outlier.rascunhospringrestapi.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil {

	@Value("${app.jwt.issuer}")
	private String issuer;

	@Value("${app.jwt.expiration}")
	private Long expiration;

	@Value("${app.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
		Date expirar = new Date();
		expirar.setTime(expirar.getTime() + expiration);

		return Jwts.builder().setIssuer(issuer).setIssuedAt(new Date()).setExpiration(expirar).setSubject(usuarioLogado.getId().toString())
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	/**
	 * Valida o token JWT e retorna o Id do usuário caso seja válido.
	 * 
	 * @param token
	 * @return idUsuario
	 */
	public Long validarToken(String token) {
		if (token == null) {
			return null;
		}
		try {
			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return Long.valueOf(parseClaimsJws.getBody().getSubject());
		} catch (Exception e) {
			return null;
		}
	}
}
