package br.com.outlier.rascunhospringrestapi.controller;

import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
class AutenticacaoControllerTest extends GenericControllerTest {

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	@Test
	public void givenCredentialsShouldSuccessfullyLogin() throws Exception {
		URI uri = new URI("/auth");
		Map<String, String> credentials = Map.of("email", "ersilvafilho@gmail.com", "senha", "123456");

		ResultActions loginResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).content(objectMapper.writeValueAsString(credentials)).contentType(MediaType.APPLICATION_JSON));

		loginResult.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	@Test
	public void givenCredentialsShouldReturnUnauthorized() throws Exception {
		URI uri = new URI("/auth");
		Map<String, String> credentials = Map.of("email", "ersilvafilho@gmail.com", "senha", "abc123");

		ResultActions loginResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).content(objectMapper.writeValueAsString(credentials)).contentType(MediaType.APPLICATION_JSON));

		loginResult.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
}
