package br.com.agrego.poc.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.agrego.poc.model.Cargo;

//@Repository
public interface CargoPagRepository extends PagingAndSortingRepository<Cargo, Long> {
	Cargo findByTitulo(String titulo);
}

