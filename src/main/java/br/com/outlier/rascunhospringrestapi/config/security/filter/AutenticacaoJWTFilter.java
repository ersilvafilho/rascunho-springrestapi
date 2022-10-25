package br.com.outlier.rascunhospringrestapi.config.security.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.outlier.rascunhospringrestapi.model.entity.Usuario;
import br.com.outlier.rascunhospringrestapi.repository.UsuarioRepository;
import br.com.outlier.rascunhospringrestapi.util.TokenUtil;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class AutenticacaoJWTFilter extends OncePerRequestFilter {

	private TokenUtil tokenUtil;
	private UsuarioRepository usuarioRepository;

	public AutenticacaoJWTFilter(TokenUtil tokenUtil, UsuarioRepository usuarioRepository) {
		this.tokenUtil = tokenUtil;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		log.debug("Buscando JWT");
		String token = getToken(request);

		if (token != null) {

			log.debug("Token encontrado. Validando.");
			Long idUsuario = tokenUtil.validarToken(token);

			if (idUsuario != null) {
				log.debug("Token válido. Buscando registro de usuário.");
				Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
				SecurityContextHolder.getContext()
						.setAuthentication(new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha(), usuario.getPerfis()));
			}

		} else {
			log.debug("Token não encontrado no header. Exibindo headers.");
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String h = headerNames.nextElement();
				log.debug(String.format("%s (%s) => %s", h, h.length(), request.getHeader(h)));
			}
		}

		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		token = (token != null) ? token : request.getHeader("authorization");

		if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
			log.debug(String.format("header authorization não informado, vazio ou em formato inválido => %s", token));
			return null;
		}

		return token.substring(7, token.length());
	}
}
