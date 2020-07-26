package br.com.agrego.poc.endpoint.v1.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrego.poc.repository.acesso.PerfilRepositoryImp;

@RestController
@RequestMapping("/v1/perfis")
public class PerfilEndpoint {

	@Autowired
	private PerfilRepositoryImp perfilRepository;
	
}
