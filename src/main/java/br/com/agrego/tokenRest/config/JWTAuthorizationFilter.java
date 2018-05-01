package br.com.agrego.tokenRest.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.agrego.tokenRest.config.security.SecurityConstants;
import br.com.agrego.tokenRest.model.Usuario;
import br.com.agrego.tokenRest.service.UsuarioService;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private final UsuarioService usuarioService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UsuarioService customUserDetailService) {
		super(authenticationManager);
		this.usuarioService = customUserDetailService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		if (header==null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authenticationToken = geAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken geAuthenticationToken(HttpServletRequest request){
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if (token == null) return null;
		String username = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
				.getBody()
				.getSubject();
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(username);
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, usuario.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return username != null ? auth:null;
				
	}

}
