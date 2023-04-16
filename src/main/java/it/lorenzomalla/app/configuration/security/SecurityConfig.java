package it.lorenzomalla.app.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.lorenzomalla.app.constants.Constant.Role;
import it.lorenzomalla.app.entity.enumaration.ERole;
import it.lorenzomalla.app.repository.CustomerRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AuthEntryPointJwtUnauth unauthorizedHandler;
	
	@Autowired
	private AuthAccessForbidden authAccessDenied;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				.accessDeniedHandler(authAccessDenied)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(new AuthorizationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),customerRepository),UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/public/**").permitAll()
				.antMatchers("/v1/api/admin/**")
				.hasAnyAuthority(Role.ADMIN)
				.antMatchers("/v1/api/user/**")
				.hasAnyAuthority(Role.ADMIN,Role.USER)
				.anyRequest()
				.authenticated();
		return http.build();
	}

}