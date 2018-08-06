package br.com.agrego.tokenRest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	List<Pessoa> findByNomeContainingIgnoreCase(String nome);
}
