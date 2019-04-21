package br.com.agrego.tokenRest.service;

import java.io.Serializable;

import br.com.agrego.tokenRest.repository.AbstractJpaDAO2;

//public abstract class AbstraticService<E extends Serializable, R extends AbstractJpaDAO2<E, JpaRepository<E, Long>>>  {
//public abstract class AbstraticService<E extends Serializable, RR extends AbstractJpaDAO2<E, JpaRepository<E,Long>>>  {
public abstract class AbstraticService<E extends Serializable, RR extends AbstractJpaDAO2<E, ?>>  {

	
}
