package br.com.agrego.tokenRest.endpoint;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrego.tokenRest.error.ResourceNotFoundException;
import br.com.agrego.tokenRest.model.Cargo;
import br.com.agrego.tokenRest.model.Pessoa;
import br.com.agrego.tokenRest.model.acesso.Permissoes;
import br.com.agrego.tokenRest.repository.PessoaRepositoryImp;

@RestController
@RequestMapping("/v1/pessoas")
public class PessoaEndpoint {

	@Autowired
	private PessoaRepositoryImp pessoaRepository;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority("+Permissoes.PESSOA_CREATE+")")
	public Pessoa create(@Valid @RequestBody Pessoa pessoa) {
		Pessoa bean = pessoaRepository.save(pessoa);
		return bean;
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.PESSOA_UPDATE+")")
	public Pessoa update(@RequestBody Pessoa pessoa) {
		Pessoa bean = pessoaRepository.update(pessoa);
		return bean;
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.PESSOA_UPDATE+"')")
	public Pessoa updateParamGeneric(@PathVariable("id") Long id, @RequestBody Map<String, Object> parametros) throws IllegalAccessException, InvocationTargetException {

		Pessoa beanAtual=null;
		if (id!=null) beanAtual = pessoaRepository.findOne(id);
		if (beanAtual == null) throw new ResourceNotFoundException(id);
		if (parametros.containsKey("id")) throw new IllegalArgumentException("Não é possível atualizar o campo ID do item");
		
		BeanUtils.populate(beanAtual,parametros);

		Pessoa bean = pessoaRepository.save(beanAtual);
		
		return bean;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.PESSOA_DELETE+")")
	public Pessoa delete(@PathVariable("id") Long id) {
		Pessoa bean = pessoaRepository.findOne(id);

		if (bean == null) throw new ResourceNotFoundException(id);
		
		pessoaRepository.delete(bean);

		return bean;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.PESSOA_READ+")")
	public List<Pessoa> findAll() {
		List<Pessoa> lista = pessoaRepository.findAll();
		return lista;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.PESSOA_READ+")")
	public Pessoa findById(@PathVariable("id") Long id){
		Pessoa bean = pessoaRepository.findOne(id);

		if (bean == null) throw new ResourceNotFoundException(id);
		
		return bean;
	}
	
	@GetMapping("/find")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_READ+"')")
	public List<Pessoa> findByNome(@RequestParam("nome") String nome){
		List<Pessoa> lista = pessoaRepository.pesquisarPorNome(nome);
		return lista;
	}

	@GetMapping("/{id}/cargo")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.PESSOA_READ+")")
	public Cargo findCargoByPessoa(@PathVariable("id") Long id){
		Pessoa bean = pessoaRepository.findOne(id);
		if (bean == null) throw new ResourceNotFoundException(id);
		return bean.getCargo();
	}

}
