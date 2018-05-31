package br.com.agrego.tokenRest.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.agrego.tokenRest.config.security.SecurityConstants;
import br.com.agrego.tokenRest.model.acesso.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
			Authentication authenticate = this.authenticationManager.authenticate(auth);
			return authenticate;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username = ((Usuario) authResult.getPrincipal()).getUsername();
		
		Date expiration = new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME);
		
		String token = Jwts
				.builder()
				.setSubject(username)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.compact();
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+token);
		response.getWriter().append(SecurityConstants.HEADER_STRING + SecurityConstants.TOKEN_PREFIX+token);
	}
}
