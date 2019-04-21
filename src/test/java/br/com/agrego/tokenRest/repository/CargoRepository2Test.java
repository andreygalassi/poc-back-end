package br.com.agrego.tokenRest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
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
//@AutoConfigureTestDatabase
public class CargoRepository2Test {

	@Autowired
	private CargoRepositoryImp2 cargoRepository;

	@Test
	public void devePersistirUmNovoCargo() {
		String titulo = "Cargo Novo";
		Cargo cargo = Cargo.newInstance(titulo);
		cargoRepository.getRepo().save(cargo);
		assertThat(cargo.getId()).isNotNull();
		assertThat(cargo.getTitulo()).isEqualTo(titulo);

		List<Cargo> findAll = cargoRepository.findAll();
		assertThat(findAll.stream().filter(c -> c.getTitulo().equals(titulo)).count()).isNotEqualTo(0l);
	}

}
