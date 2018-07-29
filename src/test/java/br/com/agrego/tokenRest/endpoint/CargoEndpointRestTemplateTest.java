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

@RunWith(SpringRunner.class)
//Inicia o endpoint com uma porta aleatória, evita problemas quando o ambiente já está em pé
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CargoEndpointRestTemplateTest {

	//2 ferramentas para testar endpoint (resttemplate e mockmvc) exemplos com as 2
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	@MockBean
	private CargoRepositoryImp cargoRepo;
	
	//Configura o restTemplate para usar autentitação basica
	@TestConfiguration
	static class Config{
		@Bean
		public RestTemplateBuilder restTemplateBuilder(){
			return new RestTemplateBuilder().basicAuthorization("admin", "123");
		}
	}
	
	@Before
	public void setup(){
		Cargo cargo = Cargo.newInstance(1l,"Cargo mock 1");
		BDDMockito.when(cargoRepo.findOne(cargo.getId())).thenReturn(cargo);
	}
	
	@Test
	public void deveRetornarStatusCode401QuandoListarCargoUsandoUsuarioSenhaInvalidos(){
		restTemplate = restTemplate.withBasicAuth("1", "1");
		ResponseEntity<String> res = restTemplate.getForEntity("/v1/cargos", String.class);
		
		assertThat(res.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void deveRetornarStatusCode401QuandoBuscaCargoPorIdUsandoUsuarioSenhaInvalidos(){
		restTemplate = restTemplate.withBasicAuth("1", "1");
		ResponseEntity<String> res = restTemplate.getForEntity("/v1/cargos/1", String.class);
		
		assertThat(res.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void deveRetornarStatusCode200QuandoListarCargo(){
		List<Cargo> listaMokada = Arrays.asList(Cargo.newInstance(1l,"Cargo mock 1"),Cargo.newInstance(2l,"Cargo mock 2"));
		BDDMockito.when(cargoRepo.findAll()).thenReturn(listaMokada);
		ResponseEntity<String> res = restTemplate.getForEntity("/v1/cargos", String.class);
		
		assertThat(res.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode200QuandoBuscaCargoPorId(){
		ResponseEntity<String> res = restTemplate.getForEntity("/v1/cargos/{id}", String.class, 1l);
		
		assertThat(res.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode404QuandoBuscaCargoPorIdInvalido(){
		ResponseEntity<Cargo> res = restTemplate.getForEntity("/v1/cargos/{id}", Cargo.class, -1);
		assertThat(res.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void deveRetornarStatusCode200QuandoDeletarCargoPorIdComUsuarioAdmin(){
		BDDMockito.doNothing().when(cargoRepo).delete(1l);
		ResponseEntity<String> exc = restTemplate.exchange("/v1/cargos/{id}", HttpMethod.DELETE,null,String.class,1l);
		Assertions.assertThat(exc.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode404QuandoDeletarCargoPorIdInexistenteComUsuarioAdmin(){
		BDDMockito.doNothing().when(cargoRepo).delete(1l);
		
		ResponseEntity<String> exc = restTemplate.exchange("/v1/cargos/{id}", HttpMethod.DELETE,null,String.class,-1l);
		Assertions.assertThat(exc.getStatusCodeValue()).isEqualTo(404);
	}
	
	//TODO aprender a tratar os teste com roles dinamicas
	@Test
	public void deveRetornarStatusCode401QuandoDeletarCargoPorIdInexistenteComUsuarioSemPermissaoAdminUsando() {
		restTemplate = restTemplate.withBasicAuth("1", "1");
		BDDMockito.doNothing().when(cargoRepo).delete(1l);

		ResponseEntity<String> exc = restTemplate.exchange("/v1/cargos/{id}", HttpMethod.DELETE,null,String.class,-1l);
		Assertions.assertThat(exc.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void deveRetornarStatusCode400QuandoSalvarCargoComTituloNull() {
		Cargo cargo = Cargo.newInstance(null,null);
		BDDMockito.when(cargoRepo.save(cargo)).thenReturn(cargo);
		ResponseEntity<String> res = restTemplate.postForEntity("/v1/cargos", cargo, String.class);
		assertThat(res.getStatusCodeValue()).isEqualTo(400);
		assertThat(res.getBody()).contains("fieldMessage","Título do cargo é obrigatório");
	}
	
	@Test
	public void deveRetornarStatusCode201QuandoSalvarCargo() {
		Cargo cargo = Cargo.newInstance(3l,"Cargo mock 1");
		BDDMockito.when(cargoRepo.save(cargo)).thenReturn(cargo);
		ResponseEntity<Cargo> res = restTemplate.postForEntity("/v1/cargos", cargo, Cargo.class);
		assertThat(res.getStatusCodeValue()).isEqualTo(201);
		assertThat(res.getBody().getId()).isNotNull();
	}
	
}
