package it.lorenzomalla.event.constants;

public class JwtConstants {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_SECRET = "yourJwtSecretKey";
	public static final String AUTH_LOGIN_URL = "/auth/login";

	// Set the validity of the token in milliseconds
	public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000;
	public static final String AUTHORITIES_KEY = "ROLE_ADMIN,ROLE_USER";
}
