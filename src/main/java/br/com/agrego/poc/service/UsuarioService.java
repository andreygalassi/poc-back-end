package br.com.agrego.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.agrego.poc.model.acesso.Usuario;
import br.com.agrego.poc.repository.acesso.UsuarioRepositoryImp;

@Component
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioRepositoryImp usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Fazer a buscar por usuario aqui
		Usuario usuario = usuarioRepository.getRepo().findByUsername(username);
//		Usuario usuario = new Usuario();
//		usuario.setUsername("admin1");//$2a$10$Ta40/f3Wp/7032xQOykVCuGTzpzkXxIg8SIE1rN4n51pZKl77vJeC
//		usuario.setPassword("$2a$10$Ta40/f3Wp/7032xQOykVCuGTzpzkXxIg8SIE1rN4n51pZKl77vJeC");//123
//		usuario.setPassword("123");//123
//		List<GrantedAuthority> perfis = AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
//		List<GrantedAuthority> authoritys = AuthorityUtils.createAuthorityList("CRIAR","EDITAR","LER");
//
//		usuario.setAuthorities(authoritys);
//		usuario.setPerfiis2(perfis);
		return usuario;
	}
}
