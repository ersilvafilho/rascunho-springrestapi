package br.com.outlier.rascunhospringrestapi.controller;

import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import br.com.outlier.rascunhospringrestapi.form.TopicoCreateForm;

@SpringBootTest
@ActiveProfiles("test")
class TopicoControllerTest extends GenericControllerTest {

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	@Test
	public void shouldListTopicos() throws Exception {

		String token = authenticate();
		URI uri = new URI("/topicos");

		String jsonResposta = mockMvc
				.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

		JSONObject json = new JSONObject(jsonResposta);
		JSONArray topicosJSON = json.getJSONObject("responseContent").getJSONArray("topicos");
		Assert.isTrue(topicosJSON.length() > 0, "Nenhum tópico listado.");
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------//
	@Test
	public void givenTopicoShouldCadastrar() throws Exception {

		String token = authenticate();
		URI uri = new URI("/topicos");

		TopicoCreateForm form = new TopicoCreateForm("Novo Tópico", "Testing TopicosController.cadastrar", 1l, 1l);
		String formJson = objectMapper.writeValueAsString(form);

		// post
		MvcResult andReturn = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(formJson)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		// response
		JSONObject jsonTopico = new JSONObject(new String(andReturn.getResponse().getContentAsString())).getJSONObject("responseContent")
				.getJSONObject("topico");
		String tituloCadastrado = jsonTopico.getString("titulo");
		String mensagemCadastrada = jsonTopico.getString("mensagem");
		Long cursoIdCadastrado = jsonTopico.getJSONObject("curso").getLong("id");
		Long autorIdCadastrado = jsonTopico.getJSONObject("autor").getLong("id");

		Assert.isTrue(
				form.getTitulo().equals(tituloCadastrado) && form.getMensagem().equals(mensagemCadastrada)
						&& form.getCursoId().equals(cursoIdCadastrado) && form.getAutorId().equals(autorIdCadastrado),
				String.format("Tópico foi cadastrado, mas o registro é diferente do esperado. Esperado -> <%s>, Atual -> <%s>", form, jsonTopico));
	}

}
