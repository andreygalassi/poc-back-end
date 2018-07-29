package br.com.agrego.tokenRest.endpoint;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
import br.com.agrego.tokenRest.model.acesso.Permissoes;
import br.com.agrego.tokenRest.repository.CargoRepositoryImp;
import javassist.NotFoundException;

@RestController
@RequestMapping("/v1/cargos")
public class CargoEndpoint {
	

	@Autowired
	private CargoRepositoryImp cargoRepository;
	
	/**
	 * 
	 * @param cargo
	 * @return json do objeto criado
	 * @status created(202) quando objeto é criado
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_CREATE+"')")
	public Cargo create(@Valid @RequestBody Cargo cargo) {
		Cargo bean = cargoRepository.save(cargo);
		return bean;
	}

	/**
	 * 
	 * @param cargo
	 * @return json do objeto atualizado.
	 * @status ok(200)
	 */
	//TODO criar teste caso o objeto não exista
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_UPDATE+"')")
	public Cargo update(@RequestBody Cargo cargo) {
		Cargo bean = cargoRepository.update(cargo);
		return bean;
	}

	/**
	 * 
	 * @param cargo
	 * @return json do objeto atualizado.
	 * @status ok(200)
	 */
	//TODO criar teste caso o objeto não exista
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_UPDATE+"')")
	public Cargo updateParamGeneric(@PathVariable("id") Long id, @RequestBody Map<String, Object> parametros) throws IllegalAccessException, InvocationTargetException {

		Cargo cargoAtual=null;
		if (id!=null) cargoAtual = cargoRepository.findOne(id);
		if (cargoAtual == null) throw new ResourceNotFoundException(id);
		if (parametros.containsKey("id")) throw new IllegalArgumentException("Não é possível atualizar o campo ID do item");
		
		BeanUtils.populate(cargoAtual,parametros);

		Cargo bean = cargoRepository.save(cargoAtual);
		
		return bean;
//	    return ResponseEntity.ok(bean);
	}
	
	/**
	 * 
	 * @param id
	 * @return 
	 * @status notFound(404) caso o objeto não exista na base.
	 * @status ok(200) caso o objeto exista na base.
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_DELETE+"')")
	public Cargo delete(@PathVariable("id") Long id) {
		Cargo cargo = cargoRepository.findOne(id);

		if (cargo == null) throw new ResourceNotFoundException(id);
//			return ResponseEntity.notFound().build();
		
		cargoRepository.delete(cargo);

		return cargo;
//		return ResponseEntity.ok().build();
	}
	
	/**
	 * 
	 * @return uma lista com o objeto
	 * @status ok(200)
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_READ+"')")
	public List<Cargo> findAll() {
		List<Cargo> findAll = cargoRepository.findAll();
		return findAll;
	}

	/**
	 * 
	 * @param id
	 * @return json do objeto pesquisado
	 * @throws NotFoundException 
	 * @status ok(200) caso existe um objeto com o id passado
	 * @status notFound(404) caso não existe um objeto com o id passado
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_READ+"')")
//	@ExceptionHandler(ResourceNotFoundException.class)
	public Cargo findById(@PathVariable("id") Long id) {
		Cargo cargo = cargoRepository.findOne(id);
		
		//tratamento capturado pelo RestExceptionHandler
		if (cargo == null) throw new ResourceNotFoundException(id);
//			return ResponseEntity.notFound().build();
		
		return cargo;
//		return ResponseEntity.ok(cargo);
//		ResponseEntity<Cargo> responseEntity = new ResponseEntity<>(cargo,HttpStatus.OK);
//		return responseEntity;
	}

	/**
	 * 
	 * @param id
	 * @return json do objeto pesquisado
	 * @status ok(200) caso existe um objeto com o id passado
	 * @status notFound(404) caso não existe um objeto com o id passado
	 */
	@GetMapping("/find")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_READ+"')")
	public List<Cargo> findByTitulo(@RequestParam("titulo") String titulo){
		List<Cargo> lista = cargoRepository.pesquisarPorTitulo(titulo);
		return lista;
	}

	
	
	@GetMapping("/teste/{id}")
	@Transactional
	public Cargo testeTransaction(@PathVariable("id") Long id, @RequestParam("descricao") String descricao) {
		Cargo cargo = cargoRepository.testeTransaction(id, descricao);
		cargo.setDescricao(descricao + "ddbc");
		cargoRepository.update(cargo);
		return cargo;
	}
}
