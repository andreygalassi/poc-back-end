package br.com.agrego.tokenRest.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrego.tokenRest.model.Cargo;
import br.com.agrego.tokenRest.repository.CargoRepositoryImp;

@RestController
@RequestMapping("/v1/cargos")
public class CargoEndpoint {

	@Autowired
	private CargoRepositoryImp cargoRepository;


	@GetMapping("/teste/{id}")
	@Transactional
	public Cargo testeTransaction(@PathVariable("id") Long id, @RequestParam("descricao") String descricao) {
		Cargo cargo = cargoRepository.testeTransaction(id, descricao);
		cargo.setDescricao(descricao + "ddbc");
		cargoRepository.update(cargo);
		return cargo;
	}
	
	@GetMapping
	public List<Cargo> findAll() {
		List<Cargo> findAll = cargoRepository.findAll();
		return findAll;
	}

	@GetMapping("/{id}")
	public Cargo findById(@PathVariable("id") Long id){
		Cargo one = cargoRepository.findOne(id);
		return one;
	}

	@GetMapping("/find")
	public List<Cargo> findByTitulo(@RequestParam("titulo") String titulo){
		List<Cargo> lista = cargoRepository.pesquisarPorTitulo(titulo);
		return lista;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cargo save(@RequestBody Cargo cargo) {
		Cargo bean = cargoRepository.save(cargo);
		return bean;
	}

	@PutMapping
	public Cargo update(@RequestBody Cargo cargo) {
		Cargo bean = cargoRepository.update(cargo);
		return bean;
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(Long id) {
		cargoRepository.delete(id);
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		return responseEntity;
	}
}
