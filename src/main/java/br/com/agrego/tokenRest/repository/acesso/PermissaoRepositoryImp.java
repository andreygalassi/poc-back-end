package br.com.agrego.tokenRest.repository.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.acesso.Permissao;
import br.com.agrego.tokenRest.repository.AbstractJpaDAO;

@Repository
public class PermissaoRepositoryImp extends AbstractJpaDAO<Permissao>  {

	@Autowired
	private PermissaoRepository repo;

	public PermissaoRepository getRepo() {
		return repo;
	}

}
