package br.com.agrego.poc.repository.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.poc.model.acesso.Usuario;
import br.com.agrego.poc.repository.AbstractJpaDAO;

@Repository
public class UsuarioRepositoryImp extends AbstractJpaDAO<Usuario>  {

	@Autowired
	private UsuarioRepository repo;

	public UsuarioRepository getRepo() {
		return repo;
	}
	
}
