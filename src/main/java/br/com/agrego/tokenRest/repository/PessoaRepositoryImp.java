package br.com.agrego.tokenRest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.Pessoa;

@Repository
public class PessoaRepositoryImp extends AbstractJpaDAO<Pessoa>  {

	@Autowired
	private PessoaRepository repo;

	public PessoaRepository getRepo() {
		return repo;
	}

	
}
