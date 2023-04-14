package it.lorenzomalla.app.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.lorenzomalla.app.exception.VehicleRuntimeException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthEntryPointJwtUnauth implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		VehicleRuntimeException exception = new VehicleRuntimeException("401", "You are not authorized",
				HttpStatus.UNAUTHORIZED);
		objectMapper.writeValue(response.getOutputStream(), exception.getError());
	}
}