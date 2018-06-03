package br.com.agrego.tokenRest.repository.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.acesso.Usuario;
import br.com.agrego.tokenRest.repository.AbstractJpaDAO;

@Repository
public class UsuarioRepositoryImp extends AbstractJpaDAO<Usuario>  {

	@Autowired
	private UsuarioRepository repo;

	public UsuarioRepository getRepo() {
		return repo;
	}
	
}
