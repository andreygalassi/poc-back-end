package br.com.agrego.tokenRest.config;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrego.tokenRest.model.Cargo;
import br.com.agrego.tokenRest.model.Perfil;
import br.com.agrego.tokenRest.model.Permissao;
import br.com.agrego.tokenRest.model.Pessoa;
import br.com.agrego.tokenRest.model.Usuario;
import br.com.agrego.tokenRest.repository.CrudCargoRepository;
import br.com.agrego.tokenRest.repository.CrudPessoaRepository;
import br.com.agrego.tokenRest.repository.CrudUsuarioRepository;

@Component
public class DadosIniciais implements InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(DadosIniciais.class);

	@Autowired
	private CrudUsuarioRepository usuarioRepository;
	
	@Autowired
	private CrudPessoaRepository pessoaRepository;
	@Autowired
	private CrudCargoRepository cargoRepository;
	
	@Override
	@Transactional
	public void afterPropertiesSet() throws Exception {
		log.info("Excutando Dados Iniciais");
		
		pessoaInit();
		usuarioInit();

		
	}
	
    public void init() {
		log.info("Teste INIT");
    }
    
    private void pessoaInit(){
		if (pessoaRepository.count()>0) return;
		
		Cargo cargo1 = Cargo.newInstance("Cargo1");
		Cargo cargo2 = Cargo.newInstance("Cargo2");
		Cargo cargo3 = Cargo.newInstance("Cargo3");
		
		try {
			List<Pessoa> pessoas = new ArrayList<>();
			pessoas.add(Pessoa.newInstance("Pessoa1", "01/01/2000", cargo1));
			pessoas.add(Pessoa.newInstance("Pessoa2", "02/01/2000", cargo2));
			pessoas.add(Pessoa.newInstance("Pessoa3", "03/01/2000", cargo3));
			pessoas.add(Pessoa.newInstance("Pessoa4", "04/01/2000", cargo1));
			pessoas.add(Pessoa.newInstance("Pessoa5", "05/01/2000", cargo2));
			pessoas.add(Pessoa.newInstance("Pessoa6", "06/01/2000", cargo2));
			
			cargoRepository.save(cargo1);
			cargoRepository.save(cargo2);
			cargoRepository.save(cargo3);
			
			pessoaRepository.save(pessoas);
			
		} catch (ParseException e) {
			log.error("Erro ao iniciar pessoas, erro no parse de data",e);
		}
    }
    
    private void usuarioInit(){
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

}
