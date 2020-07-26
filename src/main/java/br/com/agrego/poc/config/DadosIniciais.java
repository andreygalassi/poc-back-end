package br.com.agrego.poc.config;

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

import br.com.agrego.poc.model.Cargo;
import br.com.agrego.poc.model.Colaborador;
import br.com.agrego.poc.model.acesso.Perfil;
import br.com.agrego.poc.model.acesso.Permissao;
import br.com.agrego.poc.model.acesso.Permissoes;
import br.com.agrego.poc.model.acesso.Usuario;
import br.com.agrego.poc.repository.CargoRepository;
import br.com.agrego.poc.repository.ColaboradorRepository;
import br.com.agrego.poc.repository.acesso.UsuarioRepository;

//@Component
public class DadosIniciais implements InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(DadosIniciais.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ColaboradorRepository pessoaRepository;
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
			List<Colaborador> pessoas = new ArrayList<>();
			pessoas.add(Colaborador.newInstance("Pessoa1", "Sobrenome1", "p1@p1.com", "10.00", "01/01/2000", cargo1));
			pessoas.add(Colaborador.newInstance("Pessoa2", null, null, "150.20", "02/01/2000", cargo2));
			pessoas.add(Colaborador.newInstance("Pessoa3", null, "p3@p3.com", "2500.00", "03/01/2000", cargo3));
			pessoas.add(Colaborador.newInstance("Pessoa4", "Sobrenome4", "p4@p4.com", "12345.6789", "04/01/2000", cargo1));
			pessoas.add(Colaborador.newInstance("Pessoa5", "Sobrenome5", "p5@p5.com", null, "05/01/2000", cargo2));
			pessoas.add(Colaborador.newInstance("Pessoa6", "Sobrenome6", "p6@p6.com", "10.00", "06/01/2000", cargo2));
			
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
		
		Usuario semPermissao = new Usuario();
		semPermissao.setNome("semPermissao");
		semPermissao.setUsername("semPermissao");
		semPermissao.setPassword(defaultPassword);
		
		Perfil pAdmin = new Perfil();
		pAdmin.setNome("Admin");

		Perfil pUsuario = new Perfil();
		pUsuario.setNome("Usuario");
		
		Permissao createCargo = Permissao.newInstance(Permissoes.CARGO_CREATE);
		Permissao readCargo   = Permissao.newInstance(Permissoes.CARGO_READ);
		Permissao updateCargo = Permissao.newInstance(Permissoes.CARGO_UPDATE);
		Permissao deleteCargo = Permissao.newInstance(Permissoes.CARGO_DELETE);

		Permissao createPessoa = Permissao.newInstance(Permissoes.COLABORADOR_CREATE);
		Permissao readPessoa   = Permissao.newInstance(Permissoes.COLABORADOR_READ);
		Permissao updatePessoa = Permissao.newInstance(Permissoes.COLABORADOR_UPDATE);
		Permissao deletePessoa = Permissao.newInstance(Permissoes.COLABORADOR_DELETE);
		
		pAdmin.getPermissoes().add(createCargo);
		pAdmin.getPermissoes().add(readCargo);
		pAdmin.getPermissoes().add(updateCargo);
		pAdmin.getPermissoes().add(deleteCargo);

		pAdmin.getPermissoes().add(createPessoa);
		pAdmin.getPermissoes().add(readPessoa);
		pAdmin.getPermissoes().add(updatePessoa);
		pAdmin.getPermissoes().add(deletePessoa);
		
		pUsuario.getPermissoes().add(readCargo);
		pUsuario.getPermissoes().add(readPessoa);
		
		admin.getPerfis().add(pAdmin);
		admin.getPerfis().add(pUsuario);

		usuario.getPerfis().add(pUsuario);
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(admin);
		usuarios.add(usuario);
		usuarios.add(semPermissao);
		usuarioRepository.save(usuarios);
    }

}
