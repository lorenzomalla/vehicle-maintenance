package it.lorenzomalla.app.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.lorenzomalla.app.model.ErrorModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRuntimeException extends RuntimeException implements HandledException {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpStatus;
	private ErrorModel error;

	public VehicleRuntimeException(String errorCode, String errorMessage, HttpStatus httpStatus) {
		this.error = new ErrorModel(errorCode, errorMessage, null);
		this.httpStatus = httpStatus;
	}

	public VehicleRuntimeException(String errorCode, String errorMessage, Map<String, Object> additionalInfo,
			HttpStatus httpStatus) {
		this.error = new ErrorModel(errorCode, errorMessage, additionalInfo);
		this.httpStatus = httpStatus;
	}

}
