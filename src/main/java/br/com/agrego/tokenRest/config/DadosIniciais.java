package br.com.agrego.tokenRest.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrego.tokenRest.model.Perfil;
import br.com.agrego.tokenRest.model.Permissao;
import br.com.agrego.tokenRest.model.Usuario;
import br.com.agrego.tokenRest.repository.CrudUsuarioRepository;

@Component
public class DadosIniciais implements InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(DadosIniciais.class);

	@Autowired
	private CrudUsuarioRepository usuarioRepository;
	
	@Override
	@Transactional
	public void afterPropertiesSet() throws Exception {
		log.info("Excutando Dados Iniciais");
		
		if (usuarioRepository.count()>0) return;
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String defaultPassword = passwordEncoder.encode("123");
		
		Usuario admin = new Usuario();
		admin.setNome("admin");
		admin.setUsername("admin");
		admin.setPassword(defaultPassword);

		Usuario usuario = new Usuario();
		usuario.setNome("usuario");
		usuario.setUsername("usuario");
		usuario.setPassword(defaultPassword);
		
		Perfil pAdmin = new Perfil();
		pAdmin.setNome("Admin");

		Perfil pUsuario = new Perfil();
		pUsuario.setNome("Usuario");
		
		Permissao create = new Permissao();
		Permissao read = new Permissao();
		Permissao update = new Permissao();
		Permissao delete = new Permissao();
		Permissao view = new Permissao();
		create.setNome("CRIAR");
		read.setNome("LER");
		update.setNome("EDITAR");
		delete.setNome("DELETAR");
		view.setNome("VER");
		
		pAdmin.getPermissoes().add(create);
		pAdmin.getPermissoes().add(read);
		pAdmin.getPermissoes().add(update);
		pAdmin.getPermissoes().add(delete);
		pAdmin.getPermissoes().add(view);
		
		pUsuario.getPermissoes().add(read);
		pUsuario.getPermissoes().add(view);
		
		admin.getPerfis().add(pAdmin);
		admin.getPerfis().add(pUsuario);

		usuario.getPerfis().add(pUsuario);
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(admin);
		usuarios.add(usuario);
		usuarioRepository.save(usuarios);
		
	}
	
    public void init() {
		log.info("Teste INIT");
    }

}
