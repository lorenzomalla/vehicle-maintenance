package it.lorenzomalla.app.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.model.ErrorModel;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = VehicleRuntimeException.class)
	protected ResponseEntity<ErrorModel> handleVehicleRuntimeException(VehicleRuntimeException ex, WebRequest request) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getError());
	}

}