package br.com.agrego.tokenRest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.Colaborador;

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
