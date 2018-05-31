package br.com.agrego.tokenRest.repository.acesso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.acesso.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	Permissao findByNome(String nome);
}
