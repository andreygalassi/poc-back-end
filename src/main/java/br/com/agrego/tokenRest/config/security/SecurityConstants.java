package br.com.agrego.tokenRest.config.security;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 860_000_000;
	public static final String SECRET = "MySecretBlaBla123";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/login";
//	static final String SIGN_UP_URL = "/users/sign-up";
	
}
