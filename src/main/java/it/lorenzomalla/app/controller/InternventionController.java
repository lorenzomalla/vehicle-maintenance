package it.lorenzomalla.app.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.api.InterventionsApi;
import it.lorenzomalla.app.model.Intervention;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class InternventionController implements InterventionsApi {

	@Override
	public ResponseEntity<Void> createNewIntervention(String customerId, String vehicleId,
			@Valid Intervention intervention) {
		// TODO Auto-generated method stub
		return InterventionsApi.super.createNewIntervention(customerId, vehicleId, intervention);
	}

	@Override
	public ResponseEntity<Void> deleteIntervention(String customerId, String vehicleId, String interventionId) {
		// TODO Auto-generated method stub
		return InterventionsApi.super.deleteIntervention(customerId, vehicleId, interventionId);
	}

	@Override
	public ResponseEntity<Void> updateIntervention(String customerId, String vehicleId, String interventionId,
			@Valid Intervention intervention) {
		// TODO Auto-generated method stub
		return InterventionsApi.super.updateIntervention(customerId, vehicleId, interventionId, intervention);
	}
}
