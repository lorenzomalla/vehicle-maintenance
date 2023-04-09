package it.lorenzomalla.app.exception;

import org.springframework.http.HttpStatus;

import it.lorenzomalla.app.model.ErrorModel;

public interface HandledException {

	HttpStatus getHttpStatus();
	
	ErrorModel getError();
	
}
