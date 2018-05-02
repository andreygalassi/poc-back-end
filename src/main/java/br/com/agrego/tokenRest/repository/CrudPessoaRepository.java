package br.com.agrego.tokenRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.Pessoa;

@Repository
public interface CrudPessoaRepository extends JpaRepository<Pessoa, Long> {
	Pessoa findByNome(String nome);
}
