package br.com.agrego.poc.repository;

import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrego.poc.model.Cargo;

@Repository
public class CargoRepositoryImp extends AbstractJpaDAO<Cargo> {

	@Autowired
	private CargoRepository repo;

	public CargoRepository getRepo() {
		return repo;
	}
	
	@Transactional
	public Cargo testeTransaction(Long id, String descricao){
		Cargo cargo = findOne(id);
		
		cargo.setDescricao(descricao);
		
		return cargo;
	}

	@Transactional
	public Cargo testeTransaction2(Long id, String descricao){
		Cargo cargo = repo.getOne(id);
		
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

	public Page<Cargo> pesquisarPorFiltro(Pageable pageable, Cargo filtro){

		String hql = " from Cargo c where 1=1 ";
		if (filtro.getDescricao()!=null) hql += " and descricao like :descricao ";
		if (filtro.getTitulo()!=null) 	hql += " and titulo like :titulo ";
		if (filtro.getId()!=null) 		hql += " and id = :id ";
		if (filtro.getAtivo()!=null) 	hql += " and ativo = :ativo ";
		
		
		TypedQuery<Long> queryTotal = createQueryTotal("select count(c) " + hql);
		TypedQuery<Cargo> queryLista = createTypedQuery("select c " + hql);

		for (Parameter<?> p : queryLista.getParameters()) {
			if ("descricao".equals(p.getName())){queryLista.setParameter(p.getName(), filtro.getDescricao());}
			if ("titulo".equals(p.getName()))	{queryLista.setParameter(p.getName(), filtro.getTitulo());}
			if ("id".equals(p.getName()))		{queryLista.setParameter(p.getName(), filtro.getId());}
			if ("ativo".equals(p.getName()))	{queryLista.setParameter(p.getName(), filtro.getAtivo());}
		}
		
		preparaPaginacao(queryLista, pageable);
		
		Long total = queryTotal.getSingleResult();
		List<Cargo> lista = queryLista.getResultList();		
		
		Page<Cargo> page = new PageImpl<>(lista, pageable, total); 
		
		return page;
	}

}
