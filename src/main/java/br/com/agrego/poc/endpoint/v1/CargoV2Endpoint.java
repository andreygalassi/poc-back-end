package br.com.agrego.poc.endpoint.v1;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import br.com.agrego.poc.model.acesso.Permissoes;
import br.com.agrego.poc.repository.CargoRepositoryImp;

@RestController
@RequestMapping("/v2/cargos")
public class CargoV2Endpoint {

	@Autowired
	private CargoRepositoryImp cargoRepository;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
//	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_READ+"') and #oauth2.hasScope('read')")
	private Cargo pegar(@PathVariable("id") Long id) {
		return cargoRepository.findOne(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
//	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_READ+"') and #oauth2.hasScope('read')")
	private Page<Cargo> pesquisar(@PageableDefault(size = 5) Pageable pageable, @ModelAttribute Cargo filtro) {
		Page<Cargo> page = cargoRepository.pesquisarPorFiltro(pageable, filtro);
		return page;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
//	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_CREATE+"') and #oauth2.hasScope('write')")
	public Cargo criar(@Valid @RequestBody Cargo cargo) {
		Cargo bean = cargoRepository.save(cargo);
		return bean;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
//	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_UPDATE+"') and #oauth2.hasScope('write')")
	public Cargo atualizar(@PathVariable("id") Long id, @Valid @RequestBody Cargo cargo) {

		Cargo beanAtual=null;
		if (id!=null) beanAtual = cargoRepository.findOne(id);
		if (beanAtual == null) throw new ResourceNotFoundException(id);
		if (!beanAtual.getId().equals(id)) throw new ResourceNotFoundException(id);
		
		Cargo bean = cargoRepository.save(cargo);
		return bean;
	}

	@DeleteMapping("/{id}")
//	@PreAuthorize("hasAuthority('"+Permissoes.CARGO_DELETE+"') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("id") Long id) {
		Cargo bean = cargoRepository.findOne(id);
		cargoRepository.delete(bean);
	}
	
	
}
