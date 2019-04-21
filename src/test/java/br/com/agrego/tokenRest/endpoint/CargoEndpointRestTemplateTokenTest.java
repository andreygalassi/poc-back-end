package br.com.agrego.tokenRest.endpoint;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.agrego.tokenRest.model.Cargo;
import br.com.agrego.tokenRest.model.acesso.Permissoes;
import br.com.agrego.tokenRest.repository.CargoRepositoryImp;

/**
 * Classe de teste para o endpoint de cargo utilizando o restTemplate
 * @author Andrey
 * @since 29/07/2018
 */
@RunWith(SpringRunner.class)
//Inicia o endpoint com uma porta aleatória, evita problemas quando o ambiente já está em pé
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CargoEndpointRestTemplateTokenTest {

	//2 ferramentas para testar endpoint (resttemplate e mockmvc) exemplos com as 2
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	@MockBean
	private CargoRepositoryImp cargoRepo;
	
	private HttpEntity<Void> protectedHeader;
	private HttpEntity<Void> adminHeader;
	private HttpEntity<Void> wrongHeader;
	
	//configuração do header para autenticar utilizando o token
	@Before
	public void configProtectedHeaders() {
		String str = "{\"username\":\"usuario\", \"password\":\"123\" }";
		HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
		this.protectedHeader = new HttpEntity<>(headers);
	}
	@Before
	public void configAdminHeaders() {
		String str = "{\"username\":\"admin\", \"password\":\"123\" }";
		HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
		this.adminHeader = new HttpEntity<>(headers);
	}
	@Before
	public void configWrongHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "111111111");
		this.protectedHeader = new HttpEntity<>(headers);
	}
	
	@Before
	public void setup(){
		Cargo cargo = Cargo.newInstance(1l,"Cargo mock 1");
		BDDMockito.when(cargoRepo.findOne(cargo.getId())).thenReturn(cargo);
	}
	
	@Test
	public void deveRetornarStatusCode403QuandoListarCargoQuandoTokenInvalido(){
		ResponseEntity<String> res = restTemplate.exchange("/v1/cargos",HttpMethod.GET, wrongHeader, String.class);
		assertThat(res.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void deveRetornarStatusCode403QuandoBuscaCargoPorIdQuandoTokenInvalido(){
		ResponseEntity<String> res = restTemplate.exchange("/v1/cargos/1",HttpMethod.GET,wrongHeader, String.class);
		assertThat(res.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void deveRetornarStatusCode200QuandoListarCargoQuandoTokenValido(){
		ResponseEntity<String> res = restTemplate.exchange("/v1/cargos",HttpMethod.GET,protectedHeader, String.class);
		assertThat(res.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode200QuandoBuscaCargoPorIdQuandoTokenValido(){
		ResponseEntity<String> res = restTemplate.exchange("/v1/cargos/{id}",HttpMethod.GET, protectedHeader, String.class, 1l);
		assertThat(res.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode404QuandoBuscaCargoPorIdInvalidoQuandoTokenValido(){
		ResponseEntity<String> res = restTemplate.exchange("/v1/cargos/{id}",HttpMethod.GET, protectedHeader, String.class, -1l);
		assertThat(res.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void deveRetornarStatusCode200QuandoDeletarCargoPorIdComUsuarioAdmin(){
		BDDMockito.doNothing().when(cargoRepo).delete(1l);
		ResponseEntity<String> exc = restTemplate.exchange("/v1/cargos/{id}", HttpMethod.DELETE,adminHeader,String.class,1l);
		Assertions.assertThat(exc.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode404QuandoDeletarCargoPorIdInexistenteComUsuarioAdmin(){
		BDDMockito.doNothing().when(cargoRepo).delete(1l);
		
		ResponseEntity<String> exc = restTemplate.exchange("/v1/cargos/{id}", HttpMethod.DELETE,adminHeader,String.class,-1l);
		Assertions.assertThat(exc.getStatusCodeValue()).isEqualTo(404);
	}
	
	//TODO aprender a tratar os teste com roles dinamicas
	@Test
	public void deveRetornarStatusCode403QuandoDeletarCargoPorIdInexistenteComUsuarioSemPermissaoAdminUsando() {
		BDDMockito.doNothing().when(cargoRepo).delete(1l);

		ResponseEntity<String> exc = restTemplate.exchange("/v1/cargos/{id}", HttpMethod.DELETE,protectedHeader,String.class,-1l);
		Assertions.assertThat(exc.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void deveRetornarStatusCode400QuandoSalvarCargoComTituloNull() {
		Cargo cargo = Cargo.newInstance(null,null);
		BDDMockito.when(cargoRepo.save(cargo)).thenReturn(cargo);
		ResponseEntity<String> res = restTemplate.exchange("/v1/cargos", HttpMethod.POST, new HttpEntity<>(cargo,adminHeader.getHeaders()), String.class);
		assertThat(res.getStatusCodeValue()).isEqualTo(400);
		assertThat(res.getBody()).contains("fieldMessage","Título do cargo é obrigatório");
	}
	
	@Test
	public void deveRetornarStatusCode201QuandoSalvarCargo() {
		Cargo cargo = Cargo.newInstance(3l,"Cargo mock 1");
		BDDMockito.when(cargoRepo.save(cargo)).thenReturn(cargo);
		ResponseEntity<Cargo> res = restTemplate.exchange("/v1/cargos", HttpMethod.POST, new HttpEntity<>(cargo,adminHeader.getHeaders()), Cargo.class);
		assertThat(res.getStatusCodeValue()).isEqualTo(201);
		assertThat(res.getBody().getId()).isNotNull();
	}
	
}
