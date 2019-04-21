package br.com.agrego.tokenRest.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractJpaDAO2<E extends Serializable, R extends JpaRepository<E, Long>>  {
	
	@Autowired
	private R repo;
	
	public R getRepo(){
		return repo;
	}
	
	 //TODO tentar usar reflection do spring para essa situação ou criar uma api de reflection parecida com a do demoiselle
	@PersistenceContext
	EntityManager entityManager;
 
	private Class<E> beanClass;

	protected Class<E> getBeanClass() {
		
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
   
	public E findOne(long id){
		return entityManager.find(getBeanClass(), id);
	}
   
	@SuppressWarnings("unchecked")
	public List<E> findAll(){
		List<E> resultList = entityManager.createQuery( "from "+getBeanClass().getSimpleName()).getResultList();
		return resultList;
	}
 
	public void create(E entity){
	   entityManager.persist(entity);
	}
 
	public E update(E entity){
		return entityManager.merge(entity);
	}
 
	public void delete(E entity){
		entityManager.remove(entity);
	}
	
	public void deleteById(long entityId){
		E entity = findOne(entityId);
		delete(entity);
	}
}
