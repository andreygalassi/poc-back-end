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
import br.com.agrego.tokenRest.model.Pessoa;
import br.com.agrego.tokenRest.model.acesso.Perfil;
import br.com.agrego.tokenRest.model.acesso.Permissao;
import br.com.agrego.tokenRest.model.acesso.Usuario;
import br.com.agrego.tokenRest.repository.CargoRepository;
import br.com.agrego.tokenRest.repository.PessoaRepository;
import br.com.agrego.tokenRest.repository.acesso.UsuarioRepository;

@Component
public class DadosIniciais implements InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(DadosIniciais.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private CargoRepository cargoRepository;
	
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
		
		Permissao createCargo = new Permissao();
		Permissao readCargo = new Permissao();
		Permissao updateCargo = new Permissao();
		Permissao deleteCargo = new Permissao();
		Permissao viewCargo = new Permissao();
		createCargo.setNome("CARGO_CRIAR");
		readCargo.setNome("CARGO_LER");
		updateCargo.setNome("CARGO_EDITAR");
		deleteCargo.setNome("CARGO_DELETAR");
		viewCargo.setNome("CARGO_VER");
		
		pAdmin.getPermissoes().add(createCargo);
		pAdmin.getPermissoes().add(readCargo);
		pAdmin.getPermissoes().add(updateCargo);
		pAdmin.getPermissoes().add(deleteCargo);
		pAdmin.getPermissoes().add(viewCargo);
		
		pUsuario.getPermissoes().add(readCargo);
		pUsuario.getPermissoes().add(viewCargo);
		
		admin.getPerfis().add(pAdmin);
		admin.getPerfis().add(pUsuario);

		usuario.getPerfis().add(pUsuario);
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(admin);
		usuarios.add(usuario);
		usuarioRepository.save(usuarios);
    }

}
