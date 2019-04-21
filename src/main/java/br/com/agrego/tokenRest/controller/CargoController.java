package br.com.agrego.tokenRest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.agrego.tokenRest.model.Cargo;
import br.com.agrego.tokenRest.repository.CargoRepositoryImp;

@Controller
@RequestMapping("/cargos")
public class CargoController {
	
	@Autowired
	private CargoRepositoryImp repo;

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView listar(Model model) {
		List<Cargo> lista = repo.getRepo().findAll();
		ModelAndView mv = new ModelAndView("/cargo/Pesquisa");
		mv.addObject("cargos", lista);
		return mv;
    }
}
