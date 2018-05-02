package br.com.agrego.tokenRest.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.Usuario;

//@Repository
public class UsuarioRepository extends SimpleJpaRepository<Usuario, Long> {

	@Autowired
	private CrudUsuarioRepository crud;
	
	public UsuarioRepository(Class<Usuario> domainClass, EntityManager em) {
		super(domainClass, em);
	}

	public Usuario findByUsername(String username){
		return crud.findByUsername(username);
	}
	

	
}
