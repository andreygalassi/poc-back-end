package br.com.agrego.tokenRest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
	List<Colaborador> findByNomeContainingIgnoreCase(String nome);
}
