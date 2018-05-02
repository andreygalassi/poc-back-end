package br.com.agrego.tokenRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.Cargo;

@Repository
public interface CrudCargoRepository extends JpaRepository<Cargo, Long> {
	Cargo findByTitulo(String titulo);
}
