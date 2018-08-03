package br.com.agrego.tokenRest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/controle")
public class TesteController {

	@RequestMapping(method = RequestMethod.GET)
    public String testeControl(Model model) {
		
		model.addAttribute("variavel", "esse é um teste");
        return "helloWorld";
    }
}
