package br.com.agrego.tokenRest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.agrego.tokenRest.model.Cargo;

//@Repository
public interface CargoPagRepository extends PagingAndSortingRepository<Cargo, Long> {
	Cargo findByTitulo(String titulo);
}

