package br.com.agrego.tokenRest.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractJpaDAO2<T extends Serializable, E extends JpaRepository<T, Long>>  {
	
	@Autowired
	private E repo;
	
	public E getRepo(){
		return repo;
	}
	
	 //TODO tentar usar reflection do spring para essa situação ou criar uma api de reflection parecida com a do demoiselle
	@PersistenceContext
	EntityManager entityManager;
 
	private Class<T> beanClass;

	protected Class<T> getBeanClass() {
		
		if (this.beanClass == null) {
			
			this.beanClass = getGenericTypeArgument(this.getClass(), 0);
		}

		return this.beanClass;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericTypeArgument(final Class<?> clazz, final int idx) {
		final Type type = clazz.getGenericSuperclass();

		ParameterizedType paramType;
		try {
			paramType = (ParameterizedType) type;
		} catch (ClassCastException cause) {
			paramType = (ParameterizedType) ((Class<T>) type).getGenericSuperclass();
		}

		return (Class<T>) paramType.getActualTypeArguments()[idx];
	}
	public AbstractJpaDAO2() {
		this.beanClass = getBeanClass();
	}
   
	public T findOne(long id){
		return entityManager.find(getBeanClass(), id);
	}
   
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		List<T> resultList = entityManager.createQuery( "from "+getBeanClass().getSimpleName()).getResultList();
		return resultList;
	}
 
	public void create(T entity){
	   entityManager.persist(entity);
	}
 
	public T update(T entity){
		return entityManager.merge(entity);
	}
 
	public void delete(T entity){
		entityManager.remove(entity);
	}
	
	public void deleteById(long entityId){
		T entity = findOne(entityId);
		delete(entity);
	}
}
