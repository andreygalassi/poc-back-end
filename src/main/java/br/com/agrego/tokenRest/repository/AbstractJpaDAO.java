package br.com.agrego.tokenRest.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

public abstract class AbstractJpaDAO<T extends Serializable>  {
	
	 //TODO tentar usar reflection do spring para essa situação ou criar uma api de reflection parecida com a do demoiselle
	@PersistenceContext
	private EntityManager entityManager;
 
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
	public AbstractJpaDAO() {
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
 
	@Transactional
	public T save(T entity){
	   entityManager.persist(entity);
	   return entity;
	}

	@Transactional
	public T update(T entity){
		return entityManager.merge(entity);
	}

	@Transactional
	public void delete(T entity){
		entityManager.remove(entity);
	}

	@Transactional
	public void delete(Long id){
		T entity = findOne(id);
		entityManager.remove(entity);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	protected Query createQuery(final String ql) {
		return getEntityManager().createQuery(ql);
	}

	protected TypedQuery<T> createTypedQuery(final String ql) {
		TypedQuery<T> createQuery = getEntityManager().createQuery(ql, beanClass);
		return createQuery;
	}
}
