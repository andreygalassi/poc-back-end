package br.com.agrego.poc.endpoint.v1;

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

import br.com.agrego.poc.error.ResourceNotFoundException;
import br.com.agrego.poc.model.Cargo;
import br.com.agrego.poc.model.Colaborador;
import br.com.agrego.poc.model.acesso.Permissoes;
import br.com.agrego.poc.repository.ColaboradorRepositoryImp;

@RestController
@RequestMapping("/v1/pessoas")
public class ColaboradorEndpoint {

	@Autowired
	private ColaboradorRepositoryImp colaboradorRepository;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority("+Permissoes.COLABORADOR_CREATE+")")
	public Colaborador create(@Valid @RequestBody Colaborador pessoa) {
		Colaborador bean = colaboradorRepository.save(pessoa);
		return bean;
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.COLABORADOR_UPDATE+")")
	public Colaborador update(@RequestBody Colaborador pessoa) {
		Colaborador bean = colaboradorRepository.update(pessoa);
		return bean;
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.COLABORADOR_UPDATE+"')")
	public Colaborador updateParamGeneric(@PathVariable("id") Long id, @RequestBody Map<String, Object> parametros) throws IllegalAccessException, InvocationTargetException {

		Colaborador beanAtual=null;
		if (id!=null) beanAtual = colaboradorRepository.findOne(id);
		if (beanAtual == null) throw new ResourceNotFoundException(id);
		if (parametros.containsKey("id")) throw new IllegalArgumentException("Não é possível atualizar o campo ID do item");
		
		BeanUtils.populate(beanAtual,parametros);

		Colaborador bean = colaboradorRepository.save(beanAtual);
		
		return bean;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.COLABORADOR_DELETE+")")
	public Colaborador delete(@PathVariable("id") Long id) {
		Colaborador bean = colaboradorRepository.findOne(id);

		if (bean == null) throw new ResourceNotFoundException(id);
		
		colaboradorRepository.delete(bean);

		return bean;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.COLABORADOR_READ+")")
	public List<Colaborador> findAll() {
		List<Colaborador> lista = colaboradorRepository.findAll();
		return lista;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.COLABORADOR_READ+")")
	public Colaborador findById(@PathVariable("id") Long id){
		Colaborador bean = colaboradorRepository.findOne(id);

		if (bean == null) throw new ResourceNotFoundException(id);
		
		return bean;
	}
	
	@GetMapping("/find")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_READ+"')")
	public List<Colaborador> findByNome(@RequestParam("nome") String nome){
		List<Colaborador> lista = colaboradorRepository.pesquisarPorNome(nome);
		return lista;
	}

	@GetMapping("/{id}/cargo")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority("+Permissoes.COLABORADOR_READ+")")
	public Cargo findCargoByPessoa(@PathVariable("id") Long id){
		Colaborador bean = colaboradorRepository.findOne(id);
		if (bean == null) throw new ResourceNotFoundException(id);
		return bean.getCargo();
	}

}
