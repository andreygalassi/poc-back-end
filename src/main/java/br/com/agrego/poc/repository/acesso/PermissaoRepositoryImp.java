package br.com.agrego.poc.repository.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.poc.model.acesso.Permissao;
import br.com.agrego.poc.repository.AbstractJpaDAO;

@Repository
public class PermissaoRepositoryImp extends AbstractJpaDAO<Permissao>  {

	@Autowired
	private PermissaoRepository repo;

	public PermissaoRepository getRepo() {
		return repo;
	}

}
