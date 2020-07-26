package br.com.agrego.poc.repository.acesso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.poc.model.acesso.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	Permissao findByNome(String nome);
}
