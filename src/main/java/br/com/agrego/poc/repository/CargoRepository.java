package br.com.agrego.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.poc.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
	Cargo findByTitulo(String titulo);
}

