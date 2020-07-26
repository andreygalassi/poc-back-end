package br.com.agrego.poc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.poc.model.Colaborador;

@Repository
public class ColaboradorRepositoryImp extends AbstractJpaDAO<Colaborador>  {

	@Autowired
	private ColaboradorRepository repo;

	public ColaboradorRepository getRepo() {
		return repo;
	}

	public List<Colaborador> pesquisarPorNome(String nome){
		return repo.findByNomeContainingIgnoreCase(nome);
	}
}
