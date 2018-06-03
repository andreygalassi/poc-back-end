package br.com.agrego.tokenRest.repository.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.acesso.Perfil;
import br.com.agrego.tokenRest.repository.AbstractJpaDAO;

@Repository
public class PerfilRepositoryImp extends AbstractJpaDAO<Perfil>  {

	@Autowired
	private PerfilRepository repo;

	public PerfilRepository getRepo() {
		return repo;
	}

}
