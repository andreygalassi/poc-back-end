package br.com.agrego.poc.repository.acesso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agrego.poc.model.acesso.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	Perfil findByNome(String nome);
}
