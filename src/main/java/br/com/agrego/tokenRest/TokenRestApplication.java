package br.com.agrego.tokenRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class TokenRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenRestApplication.class, args);
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello buddy!";
	}
}
