package br.com.agrego.poc.config.security;

public class SecurityConstants {

	public static final int EXPIRATION_TOKEN = 1800;
	public static final int EXPIRATION_REFRESH = 3600 * 24;
	public static final String SECRET = "MySecretBlaBla123";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/login";
//	static final String SIGN_UP_URL = "/users/sign-up";
	
}
