package br.com.agrego.tokenRest.repository;

import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrego.tokenRest.model.Cargo;

@Repository
public class CargoRepositoryImp extends AbstractJpaDAO<Cargo>  {

	@Autowired
	private CargoRepository repo;
	
	@Transactional
	public Cargo testeTransaction(Long id, String descricao){
		Cargo cargo = findOne(id);
		
		cargo.setDescricao(descricao);
		
		return cargo;
	}

	@Transactional
	public Cargo testeTransaction2(Long id, String descricao){
		Cargo cargo = repo.findOne(id);
		
		cargo.setDescricao(descricao);
		
		return cargo;
	}

	public List<Cargo> pesquisarPorDescricao(String descricao){

		String hql = "select c from Cargo c where descricao like :descricao";
		
//		Query query = createQuery(hql);
		TypedQuery<Cargo> query = createTypedQuery(hql);

		for (Parameter<?> p : query.getParameters()) {
			if ("descricao".equals(p.getName()))	{query.setParameter(p.getName(), descricao);}
		}

		return query.getResultList();
	}

	public List<Cargo> pesquisarPorTitulo(String titulo){
		String hql = "select c from Cargo c where titulo like :titulo";
		TypedQuery<Cargo> query = createTypedQuery(hql);
		for (Parameter<?> p : query.getParameters()) {
			if ("titulo".equals(p.getName()))	{query.setParameter(p.getName(), titulo);}
		}
		return query.getResultList();
	}

	public CargoRepository getRepo() {
		return repo;
	}

	
	

	
}
