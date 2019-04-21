package br.com.agrego.tokenRest.endpoint;

import java.awt.print.Printable;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

import br.com.agrego.tokenRest.model.Cargo;
import br.com.agrego.tokenRest.model.acesso.Permissoes;
import br.com.agrego.tokenRest.repository.CargoRepositoryImp;

/**
 * Classe de teste para o endpoint de cargo utilizando o mockmvc com autenticação via token
 * @author Andrey
 * @since 29/07/2018
 */
@RunWith(SpringRunner.class)
//Inicia o endpoint com uma porta aleatória, evita problemas quando o ambiente já está em pé
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc//utilizado para o mockMvc
public class CargoEndpointMockMvcTokenTest {

	@Autowired
	private MockMvc mockMvc;
	
	@LocalServerPort
	private int port;
	@MockBean
	private CargoRepositoryImp cargoRepo;

	private HttpEntity<Void> protectedHeader;
	private HttpEntity<Void> adminHeader;
	private HttpEntity<Void> wrongHeader;

    private HttpHeaders getResponseHeaders(MockHttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        for (String name : response.getHeaderNames()) {
            List<String> values = response.getHeaders(name);
            for (String value : values) {
                headers.add(name, value);
            }
        }
        return headers;
    }
    
	//configuração do header para autenticar utilizando o token
	@Before
	public void configProtectedHeaders() throws Exception {
		String str = "{\"username\":\"semPermissao\", \"password\":\"123\" }";
		MockHttpServletResponse response = this.mockMvc
				.perform(MockMvcRequestBuilders
					.post("/login")
					.contentType("application/json;charset=UTF-8")
					.content(str))
				.andReturn().getResponse();
		this.protectedHeader = new HttpEntity<>(getResponseHeaders(response));
	}
	@Before
	public void configAdminHeaders() throws Exception {
		String str = "{\"username\":\"admin\", \"password\":\"123\" }";
		MockHttpServletResponse response = this.mockMvc
				.perform(MockMvcRequestBuilders
					.post("/login")
					.contentType("application/json;charset=UTF-8")
					.content(str))
				.andReturn().getResponse();
		this.adminHeader = new HttpEntity<>(getResponseHeaders(response));
	}
//	@Before
	public void configWrongHeaders() throws Exception {
		String str = "{\"username\":\"outro\", \"password\":\"111111\" }";
		HttpHeaders headers = new HttpHeaders();
		MockHttpServletResponse response = this.mockMvc
				.perform(MockMvcRequestBuilders
					.post("/login")
					.contentType("application/json;charset=UTF-8")
					.content(str))
				.andReturn().getResponse();
		this.protectedHeader = new HttpEntity<>(getResponseHeaders(response));
	}
	
	@Before
	public void setup(){
		Cargo cargo = Cargo.newInstance(1l,"Cargo mock 1");
		BDDMockito.when(cargoRepo.findOne(cargo.getId())).thenReturn(cargo);
	}
	
	@Test
	public void deveRetornarStatusCode403QuandoListarCargoUsandoTokenInvalido() throws Exception{
		String token = "invalido";
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/cargos").header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isForbidden())
			;
	}
	@Test
//	@WithMockUser(username="semPermissao",password="123")
	public void deveRetornarStatusCode403QuandoListarCargoUsandoTokenSemAutorizacao() throws Exception{
		String token = protectedHeader.getHeaders().get("Authorization").get(0);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/cargos").header("Authorization", token)).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isForbidden())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/cargos")))
			;
	}
	
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={"OUTRO"})
	public void deveRetornarStatusCode403QuandoBuscaCargoPorIdUsandoTokenSemAutorizacao() throws Exception{
		String token = protectedHeader.getHeaders().get("Authorization").get(0);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/cargos/1").header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isForbidden())
			.andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/cargos/1")))
			;
	}
	
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={Permissoes.CARGO_READ})
	public void deveRetornarStatusCode200QuandoListarCargoUsandoToken() throws Exception{
		String token = adminHeader.getHeaders().get("Authorization").get(0);
		List<Cargo> listaMokada = Arrays.asList(Cargo.newInstance(1l,"Cargo mock 1"),Cargo.newInstance(2l,"Cargo mock 2"));
		BDDMockito.when(cargoRepo.findAll()).thenReturn(listaMokada);

		mockMvc.perform(MockMvcRequestBuilders.get("/v1/cargos").header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo", Matchers.is("Cargo mock 1")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].titulo", Matchers.is("Cargo mock 2")))
			;
	}
	
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={Permissoes.CARGO_READ})
	public void deveRetornarStatusCode200QuandoBuscaCargoPorId() throws Exception{
		String token = adminHeader.getHeaders().get("Authorization").get(0);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/cargos/{id}",1).header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.titulo", Matchers.is("Cargo mock 1")))
			;
	}
	
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={Permissoes.CARGO_READ})
	public void deveRetornarStatusCode404QuandoBuscaCargoPorIdInvalido() throws Exception{
		String token = adminHeader.getHeaders().get("Authorization").get(0);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/cargos/{id}",-1).header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/cargos/-1")))
			;
	}
	
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={Permissoes.CARGO_DELETE})
	public void deveRetornarStatusCode200QuandoDeletarCargoPorId() throws Exception{
		String token = adminHeader.getHeaders().get("Authorization").get(0);
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/cargos/{id}",1).header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.titulo", Matchers.is("Cargo mock 1")))
			;
	}
	
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={Permissoes.CARGO_DELETE})
	public void deveRetornarStatusCode404QuandoDeletarCargoPorIdInexistente() throws Exception{
		String token = adminHeader.getHeaders().get("Authorization").get(0);
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/cargos/{id}",-1).header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/cargos/-1")))
			;
	}
	
//	//TODO aprender a tratar os teste com roles dinamicas
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={"CARGO_OUTROS"})
	public void deveRetornarStatusCode403QuandoDeletarCargoPorIdInexistenteComUsuarioSemPermissao() throws Exception{
		String token = protectedHeader.getHeaders().get("Authorization").get(0);
		BDDMockito.doNothing().when(cargoRepo).delete(1l);
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/cargos/{id}",-1l).header("Authorization", token))
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/cargos/-1")))
				;
	}
	
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={Permissoes.CARGO_CREATE})
	public void deveRetornarStatusCode400QuandoSalvarCargoComTituloNull() throws Exception{
		String token = adminHeader.getHeaders().get("Authorization").get(0);
		Cargo cargo = Cargo.newInstance(null,null);
		BDDMockito.when(cargoRepo.save(cargo)).thenReturn(cargo);
		Gson gson = new Gson();
		mockMvc.perform(
					MockMvcRequestBuilders.post("/v1/cargos").header("Authorization", token)
					.contentType("application/json;charset=UTF-8")
					.content(gson.toJson(cargo)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.fieldMessage", Matchers.is("Título do cargo é obrigatório")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Erro na validação de campo")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/cargos")))
				;
	}
	
	@Test
//	@WithMockUser(username="xx",password="xx",authorities={Permissoes.CARGO_CREATE})
	public void deveRetornarStatusCode201QuandoSalvarCargo() throws Exception{
		String token = adminHeader.getHeaders().get("Authorization").get(0);
		Cargo cargo = Cargo.newInstance(3l,"Cargo mock 1");
		BDDMockito.when(cargoRepo.save(cargo)).thenReturn(cargo);

		Gson gson = new Gson();
		mockMvc.perform(
					MockMvcRequestBuilders.post("/v1/cargos").header("Authorization", token)
					.contentType("application/json;charset=UTF-8")
					.content(gson.toJson(cargo)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.titulo", Matchers.is("Cargo mock 1")))
				;
	}
	
}
