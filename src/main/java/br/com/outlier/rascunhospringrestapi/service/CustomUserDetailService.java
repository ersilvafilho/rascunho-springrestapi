package br.com.outlier.rascunhospringrestapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.outlier.rascunhospringrestapi.model.entity.Usuario;
import br.com.outlier.rascunhospringrestapi.repository.UsuarioRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		return usuario;
	}

}
