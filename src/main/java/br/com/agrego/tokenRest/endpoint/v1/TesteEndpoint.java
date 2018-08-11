package br.com.agrego.tokenRest.endpoint.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrego.tokenRest.model.acesso.Usuario;
import br.com.agrego.tokenRest.repository.CargoRepositoryImp;
import br.com.agrego.tokenRest.repository.acesso.UsuarioRepositoryImp;

@RestController
@RequestMapping("/teste")
public class TesteEndpoint {

	  @Autowired
	  private UsuarioRepositoryImp usuarioRepository; 
	  @Autowired
	  private CargoRepositoryImp cargoDAO; 
	  
	  @GetMapping("/t1")
//	  @Secured({"CRIAR"})
	  @PreAuthorize("hasRole('ADMIN')")
	  public String teste1(Authentication authentication){
//		  System.out.println(userDetails);, @AuthenticationPrincipal UserDetails userDetails;
		  System.out.println(authentication.getAuthorities());
		  System.out.println(authentication.getCredentials());
		  System.out.println(authentication.getPrincipal());
		  
		  
		  return "teste de segurança 1";
	  }
	  @GetMapping("/t2")
//	  @Secured({"REGRAS2"})
//	  @PreAuthorize("hasRole('REGRAS2')")
//	  @PreAuthorize("hasAuthority('CRIAR') or hasRole('CRIAR')")
	  public String teste2(){
		  return "teste de segurança 2";
	  }
	  @GetMapping("/t3")
	  @Secured({"CRIA2R"})
	  public String teste3(){
		  return "teste de segurança 3";
	  }
	  
	  @GetMapping("/t4")
	  public List<Usuario> teste4(){
		  return usuarioRepository.findAll();
	  }

}
