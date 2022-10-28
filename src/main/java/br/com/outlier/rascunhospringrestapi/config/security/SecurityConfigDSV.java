package br.com.outlier.rascunhospringrestapi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("dsv")
public class SecurityConfigDSV {

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//http.authorizeHttpRequests().anyRequest().permitAll().and().csrf().disable();

		http.authorizeHttpRequests()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers("/swagger-ui/**").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos").permitAll()
		.antMatchers(HttpMethod.POST, "/topicos/*").hasAuthority("Aluno")
		.antMatchers(HttpMethod.DELETE, "**").hasAuthority("Administrador")
		.antMatchers(HttpMethod.GET, "/usuarios").hasAnyAuthority("Administrador")
		.antMatchers("/actuator/**").permitAll()
		.anyRequest().authenticated()		
		.and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().httpBasic();

		// apenas para permitir o h2-console
		http.headers().frameOptions().disable();

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer ignoringCustomizer() {
		return (web) -> web.ignoring().antMatchers("/**.html", "/v3/**", "/webjars/**","/configuration/**", "/swagger-resources/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
