package br.com.outlier.rascunhospringrestapi.controller;

import java.net.URI;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

@SpringBootTest
@ActiveProfiles("test")
class UsuarioControllerTest extends GenericControllerTest {

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	@Test
	public void shouldListUsers() throws Exception {

		String token = authenticate();

		URI uri = new URI("/usuarios");
		ResultActions loginResult = mockMvc.perform(
				MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token));

		MvcResult andReturn = loginResult.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		JSONArray jArray = new JSONArray(andReturn.getResponse().getContentAsString());
		Assert.state(jArray.length() > 0, "Nenhum registro encontrado");
	}

}
