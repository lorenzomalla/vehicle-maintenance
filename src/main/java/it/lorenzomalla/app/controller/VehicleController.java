package it.lorenzomalla.app.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.api.VehicleApi;
import it.lorenzomalla.app.model.Vehicle;
import it.lorenzomalla.app.service.VehicleService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class VehicleController implements VehicleApi {

	private final VehicleService vehicleService;

	@Override
	public ResponseEntity<Vehicle> createVehicle(String customerId, @Valid Vehicle body) {
		return ResponseEntity.ok().body(vehicleService.createVehicle(customerId, body));
	}

	@Override
	public ResponseEntity<Vehicle> updateVehicle(String vehicleId, @Valid Vehicle body) {
		return ResponseEntity.ok().body(vehicleService.updateVehicle(vehicleId, body));
	}

}
