package br.com.agrego.tokenRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.agrego.tokenRest.model.Usuario;
import br.com.agrego.tokenRest.repository.CrudUsuarioRepository;

@Component
public class UsuarioService implements UserDetailsService{

	@Autowired
	private CrudUsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Fazer a buscar por usuario aqui
		Usuario usuario = usuarioRepository.findByUsername(username);
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
