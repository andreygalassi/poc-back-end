package br.com.agrego.tokenRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.tokenRest.model.Usuario;

@Repository
public interface CrudUsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByUsername(String username);
}
