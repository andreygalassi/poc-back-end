package br.com.agrego.tokenRest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.agrego.tokenRest.model.Cargo;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
//@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class CargoRepositoryTest {

	@Autowired
	private CargoRepositoryImp cargoRepository;
	
	/**
	 * espera receber essas excessões nos testes
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void devePersistirUmNovoCargo(){
		String titulo = "Cargo Novo";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.save(cargo);
		assertThat(cargo.getId()).isNotNull();
		assertThat(cargo.getTitulo()).isEqualTo(titulo);

		List<Cargo> findAll = cargoRepository.findAll();
		assertThat(findAll.stream().filter(c -> c.getTitulo().equals(titulo)).count()).isNotEqualTo(0l);
	}

	@Test
	public void deveRemoverUmCargoPorId(){
		String titulo = "Cargo Para Exclusão por Id";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.save(cargo);
		cargoRepository.delete(cargo.getId());
		
		assertThat(cargoRepository.findOne(cargo.getId())).isNull();
		
		List<Cargo> findAll = cargoRepository.findAll();
		assertThat(findAll.stream().filter(c -> c.getTitulo().equals(titulo)).count()).isEqualTo(0l);
	}

	@Test
	@Transactional
	public void deveRemoverUmCargoPorEntidade(){
		String titulo = "Cargo Para Exclusão por Entidade";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.save(cargo);
		cargoRepository.delete(cargo);
		
		assertThat(cargoRepository.findOne(cargo.getId())).isNull();
		
		List<Cargo> findAll = cargoRepository.findAll();
		assertThat(findAll.stream().filter(c -> c.getTitulo().equals(titulo)).count()).isEqualTo(0l);
	}

	@Test
	public void devePersistirUmNovoCargoUsandoRepo(){
		String titulo = "Cargo Novo";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.getRepo().save(cargo);
		assertThat(cargo.getId()).isNotNull();
		assertThat(cargo.getTitulo()).isEqualTo(titulo);

		List<Cargo> findAll = cargoRepository.getRepo().findAll();
		assertThat(findAll.stream().filter(c -> c.getTitulo().equals(titulo)).count()).isNotEqualTo(0l);
	}

	@Test
	public void deveRemoverUmCargoPorIdUsandoRepo(){
		String titulo = "Cargo Para Exclusão por Id";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.getRepo().save(cargo);
		cargoRepository.getRepo().delete(cargo.getId());
		
		assertThat(cargoRepository.getRepo().findOne(cargo.getId())).isNull();
		
		List<Cargo> findAll = cargoRepository.getRepo().findAll();
		assertThat(findAll.stream().filter(c -> c.getTitulo().equals(titulo)).count()).isEqualTo(0l);
	}

	@Test
	public void deveRemoverUmCargoPorEntidadeUsandoRepo(){
		String titulo = "Cargo Para Exclusão por Entidade";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.getRepo().save(cargo);
		cargoRepository.getRepo().delete(cargo);
		
		assertThat(cargoRepository.getRepo().findOne(cargo.getId())).isNull();
		
		List<Cargo> findAll = cargoRepository.getRepo().findAll();
		assertThat(findAll.stream().filter(c -> c.getTitulo().equals(titulo)).count()).isEqualTo(0l);
	}

	@Test
	public void deveAtualizarUmCargoSalvo(){
		String titulo = "Cargo para Atualização";
		String descricao = "Descrição do cargo para Atualização";
		Cargo cargo = Cargo.newInstance(titulo);
		cargo.setDescricao(descricao);
		cargo = cargoRepository.getRepo().save(cargo);
		
		cargo.setTitulo("Cargo atualizado");
		cargo.setDescricao("Descrição do cargo atualizado");
		cargo = cargoRepository.getRepo().save(cargo);
		
		assertThat(cargoRepository.getRepo().findOne(cargo.getId())).isNotNull();
		assertThat(cargoRepository.getRepo().findOne(cargo.getId()).getTitulo()).isEqualTo("Cargo atualizado");
		assertThat(cargoRepository.getRepo().findOne(cargo.getId()).getDescricao()).isEqualTo("Descrição do cargo atualizado");
		
		List<Cargo> findAll = cargoRepository.getRepo().findAll();
		assertThat(findAll.stream().filter(c -> 
			c.getTitulo().equals("Cargo atualizado") && c.getDescricao().equals("Descrição do cargo atualizado")
		).count()).isEqualTo(1l);
	}

	@Test
	public void deveLancarConstraintViolationExeptionQuandoTituloENull(){
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Título do cargo é obrigatório");
		String titulo = null;
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.getRepo().save(cargo);
	}

	@Test
	public void deveLancarConstraintViolationExeptionQuandoTituloEBranco(){
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Título do cargo é obrigatório");
		String titulo = "";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.getRepo().save(cargo);
	}

	@Test
	public void deveLancarConstraintViolationExeptionQuandoTituloTemSomenteEspacos(){
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Título do cargo é obrigatório");
		String titulo = "  ";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.getRepo().save(cargo);
	}
}
