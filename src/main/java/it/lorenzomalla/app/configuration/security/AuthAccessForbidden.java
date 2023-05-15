package it.lorenzomalla.app.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.lorenzomalla.app.constants.Constant.ErrorCode;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthAccessForbidden implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());

		VehicleRuntimeException exception = new VehicleRuntimeException(ErrorCode._404, "You don't have access to this resource",
				HttpStatus.FORBIDDEN);
		objectMapper.writeValue(response.getOutputStream(), exception.getError());

	}
}