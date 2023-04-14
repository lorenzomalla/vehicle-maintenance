package it.lorenzomalla.app.configuration.security;

import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtProvider {

	public static final String issuer = "vehicleapp";
	public static String secret;
	public static String prefix;
	public static String headerParam;

	@Autowired
	public JwtProvider(Environment env) {
		JwtProvider.secret = env.getProperty("security.secret");
		JwtProvider.prefix = env.getProperty("security.prefix");
		JwtProvider.headerParam = env.getProperty("security.param");
	}

	public static String createJwt(String subject, Map<String, Object> payloadClaims) {
		JWTCreator.Builder builder = JWT.create().withSubject(subject).withIssuer(issuer);
		final DateTime now = DateTime.now();
		builder.withIssuedAt(now.toDate()).withExpiresAt(now.plusDays(1).toDate());

		for (Map.Entry<String, Object> entry : payloadClaims.entrySet()) {
			builder.withClaim(entry.getKey(), entry.getValue().toString());
		}
		return builder.sign(Algorithm.HMAC256(JwtProvider.secret));
	}

	public static DecodedJWT verifyJwt(String jwt) {
		return JWT.require(Algorithm.HMAC256(JwtProvider.secret)).build().verify(jwt);
	}
}