package br.com.agrego.tokenRest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.agrego.tokenRest.model.Cargo;
import br.com.agrego.tokenRest.model.Pessoa;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
//@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class PessoaRepositoryTest {

	@Autowired
	private PessoaRepositoryImp pessoaRepository;
	@Autowired
	private CargoRepositoryImp cargoRepository;

	/**
	 * espera receber essas excessões nos testes
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup(){
		Cargo cargo1 = Cargo.newInstance("Cargo mock 1");
		cargoRepository.save(cargo1);
	}
	
	@Test
	public void devePersistirUmaNovaPessoa() throws ParseException{
		String nome = "Nome1";
		Cargo cargo = cargoRepository.findOne(1l);
		Pessoa bean = Pessoa.newInstance(nome, "SobreNome1","p1@p1.com","100.00","01/01/2001", cargo);
		pessoaRepository.save(bean);
		assertThat(bean.getId()).isNotNull();
		assertThat(bean.getNome()).isEqualTo(nome);
		assertThat(bean.getSobreNome()).isEqualTo("SobreNome1");
		assertThat(bean.getEmail()).isEqualTo("p1@p1.com");
		assertThat(bean.getSalario()).isEqualByComparingTo(new BigDecimal("100.00"));
		assertThat(bean.getDtNascimento()).isEqualToIgnoringHours("2001-01-01");
		assertThat(bean.getCargo().getId()).isEqualTo(1l);

		List<Pessoa> findAll = pessoaRepository.findAll();
		assertThat(findAll.stream().filter(c -> c.getNome().equals(nome)).count()).isNotEqualTo(0l);

		Pessoa one = pessoaRepository.findOne(bean.getId());
		assertThat(one.getId()).isNotNull();
		assertThat(one.getNome()).isEqualTo(nome);
		assertThat(one.getSobreNome()).isEqualTo("SobreNome1");
		assertThat(one.getEmail()).isEqualTo("p1@p1.com");
		assertThat(one.getSalario()).isEqualByComparingTo(new BigDecimal("100.00"));
		assertThat(one.getDtNascimento()).isEqualToIgnoringHours("2001-01-01");
		assertThat(one.getCargo().getId()).isEqualTo(1l);
		
	}
	
//	@Test
	public void devePersistirUmaNovoaPessoaUsandoRepo(){
	}

//	@Test
	public void deveRemoverUmaPessoaPorId(){
	}

//	@Test
	public void deveRemoverUmaPessoaPorEntidade(){
	}

//	@Test
	public void deveRemoverUmaPessoaPorIdUsandoRepo(){
	}

//	@Test
	public void deveRemoverUmaPessoaPorEntidadeUsandoRepo(){
	}

//	@Test
	public void deveAtualizarUmaPessoaSalva(){
	}

//	@Test
	public void deveLancarConstraintViolationExeptionQuandoNomeENull(){
	}

//	@Test
	public void deveLancarConstraintViolationExeptionQuandoNomeEBranco(){
	}

//	@Test
	public void deveLancarConstraintViolationExeptionQuandoNomeTemSomenteEspacos(){
	}

//	@Test
	public void deveLancarConstraintViolationExeptionQuandoEmailNaoValido(){
	}

//	@Test
	public void deveLancarConstraintViolationExeptionQuandoEmailEBranco(){
	}

//	@Test
	public void deveLancarConstraintViolationExeptionQuandoEmailTemSomenteEspacos(){
	}
}
