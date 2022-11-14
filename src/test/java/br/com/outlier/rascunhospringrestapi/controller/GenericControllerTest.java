package br.com.outlier.rascunhospringrestapi.controller;

import java.net.URI;
import java.util.Map;

import javax.servlet.Filter;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.outlier.rascunhospringrestapi.util.TokenUtil;

public class GenericControllerTest {

	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	protected Filter springSecurityFilterChain;
	
	@Autowired
	protected TokenUtil tokenUtil;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
	}

	protected String authenticate() throws Exception {
		URI uri = new URI("/auth");
		Map<String, String> credentials = Map.of("email", "ersilvafilho@gmail.com", "senha", "123456");

		ResultActions loginResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).content(objectMapper.writeValueAsString(credentials)).contentType(MediaType.APPLICATION_JSON));

		MvcResult andReturn = loginResult.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		JSONObject json = new JSONObject(andReturn.getResponse().getContentAsString());
		String token = json.getJSONObject("responseContent").get("token").toString();
		return token;
	}
}
