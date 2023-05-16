package it.lorenzomalla.app.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.lorenzomalla.app.constants.Constant.ErrorCode;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.model.ErrorModel;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = VehicleRuntimeException.class)
	protected ResponseEntity<ErrorModel> handleVehicleRuntimeException(VehicleRuntimeException ex, WebRequest request) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getError());
	}

	@ExceptionHandler(value = PSQLException.class)
	protected ResponseEntity<ErrorModel> handleRuntimeException(PSQLException psqlEx, WebRequest request) {
		ErrorModel errorModel = new ErrorModel();
		errorModel.setErrorCode(ErrorCode._500);
		errorModel.setErrorMessage("The vehicle with this plate already exist");
		errorModel.errors(buildAdditonalParams(psqlEx));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorModel);
	}

	// TODO Try to get Additional info from WebRequest
	private Map<String, Object> buildAdditonalParams(PSQLException ex) {
		// I want to get the information of element for ie: in this case This plate
		// already exist

		// TODO this is WRONG!!!
		String[] messageSplitted = ex.getMessage().split("Dettaglio: Key");
		String[] keyValue = messageSplitted[1].split("=");
		Map<String, Object> additionalParams = new HashMap<>();
		additionalParams.put(keyValue[0].replace("(", "").replace(")", ""), keyValue[1]);
		return additionalParams;
	}
	
	/*
	 * {
		"errorCode": "500",
		"errorMessage": "The vehicle with this plate already exist",
		"errors":{
			"license_plate": "(AB123CE) already exists."
		   }
		}
	 */

}