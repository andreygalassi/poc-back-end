package br.com.agrego.tokenRest.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrego.tokenRest.model.Cargo;
import br.com.agrego.tokenRest.model.Pessoa;
import br.com.agrego.tokenRest.model.acesso.EnumAcao;
import br.com.agrego.tokenRest.repository.PessoaRepositoryImp;
import ch.qos.logback.core.net.SyslogOutputStream;

@RestController
@RequestMapping("/v1/pessoas")
public class PessoaEndpoint {

	@Autowired
	private PessoaRepositoryImp pessoaRepository;

	@GetMapping
	@PreAuthorize("hasAuthority('PESSOA_LER')")
	public List<Pessoa> findAll() {
		List<Pessoa> findAll = pessoaRepository.findAll();
		return findAll;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('PESSOA_LER')")
	public Pessoa findById(@PathVariable("id") Long id){
		Pessoa one = pessoaRepository.findOne(id);
		return one;
	}

	@GetMapping("/{id}/cargos")
	@PreAuthorize("hasAuthority('PESSOA_LER')")
	public Cargo findCargoByPessoa(@PathVariable("id") Long id){
		Pessoa one = pessoaRepository.findOne(id);
		return one.getCargo();
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('PESSOA_SALVAR')")
	public Pessoa save(@RequestBody Pessoa pessoa) {
		Pessoa save = pessoaRepository.save(pessoa);
		return save;
	}

	@PutMapping
	@PreAuthorize("hasAuthority('PESSOA_EDITAR')")
	public Pessoa update(@RequestBody Pessoa pessoa) {
		Pessoa bean = pessoaRepository.update(pessoa);
		return bean;
	}
	
	@DeleteMapping
	@PreAuthorize("hasAuthority('PESSOA_DELETAR')")
	public ResponseEntity<?> delete(Long id) {
		pessoaRepository.delete(id);
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		return responseEntity;
	}
	
	public static void main(String[] args) {
		
		System.out.println(String.class.getSimpleName());
	}
}
