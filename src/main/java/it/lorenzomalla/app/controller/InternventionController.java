package it.lorenzomalla.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.api.InterventionsApi;
import it.lorenzomalla.app.constants.Constant.Endpoint;
import it.lorenzomalla.app.model.Intervention;
import it.lorenzomalla.app.service.InterventionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(Endpoint.USER)
public class InternventionController implements InterventionsApi {

	private final InterventionService interventionService;

	@Override
	public ResponseEntity<Intervention> createNewIntervention(String customerId, String vehicleId,
			@Valid Intervention intervention) {
		return ResponseEntity.status(HttpStatus.CREATED).body(interventionService.createNewIntervention(customerId, vehicleId, intervention));
	}

	@Override
	public ResponseEntity<Void> deleteIntervention(String interventionId) {
		interventionService.deleteIntervention(interventionId);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Intervention> updateIntervention(String interventionId, @Valid Intervention intervention) {
		return ResponseEntity.ok().body(interventionService.updateIntervention(interventionId, intervention));
	}
	
	@Override
	public ResponseEntity<List<Intervention>> listOfIntervention() {
		return ResponseEntity.ok().body(interventionService.getListIntervention());
	}

}
