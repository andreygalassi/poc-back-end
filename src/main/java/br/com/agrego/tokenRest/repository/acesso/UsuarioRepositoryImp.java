package br.com.agrego.tokenRest.repository.acesso;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.acesso.Usuario;
import br.com.agrego.tokenRest.repository.AbstractJpaDAO;

@Repository
public class UsuarioRepositoryImp extends AbstractJpaDAO<Usuario>  {

//	@Autowired
//	private CrudUsuarioRepository crud;

//	   @PersistenceContext
//	   EntityManager entityManager;
	   
//	public Usuario findByUsername(String username){
//		return crud.findByUsername(username);
//	}
	   
//	   public List<Usuario> findAll(){
//		   List resultList = entityManager.createQuery( "from Usuario u").getResultList();
//		      return resultList;
//		   }
	

	
}
