package it.lorenzomalla.app.configuration.security;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.lorenzomalla.app.constants.Constant.General;
import it.lorenzomalla.app.entity.enumaration.ERole;
import it.lorenzomalla.app.pojo.CustomerPojo;
import it.lorenzomalla.app.repository.CustomerRepository;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private CustomerRepository userRepository;
	private ObjectMapper objectMapper;

	public AuthorizationFilter(AuthenticationManager authenticationManager, CustomerRepository customerRepository) {
		super(authenticationManager);
		this.userRepository = customerRepository;
		this.objectMapper = new ObjectMapper();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final String authHeader = request.getHeader(JwtProvider.headerParam);
		if (StringUtils.hasText(authHeader) && StringUtils.startsWithIgnoreCase(authHeader, JwtProvider.prefix)) {
			final DecodedJWT decoded = JwtProvider.verifyJwt(authHeader.replace(JwtProvider.prefix, ""));
			final ObjectNode userNode = objectMapper.readValue(decoded.getClaim(General.CUSTOMER).asString(), ObjectNode.class);
			final CustomerPojo user = objectMapper.convertValue(userNode, CustomerPojo.class);
			this.userRepository.findById(user.getId()).ifPresent(entity -> {
				SecurityContextHolder.getContext()
						.setAuthentication(new UsernamePasswordAuthenticationToken(user, null,
								entity.getRoles()
									  .stream()
									  .map(roleEntity -> roleEntity.getName())
									  .map(ERole::name)
									  .map(SimpleGrantedAuthority::new).collect(Collectors.toSet())));
			});
		}
		chain.doFilter(request, response);
	}
}