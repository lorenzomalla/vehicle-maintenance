package it.lorenzomalla.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.api.VehicleApi;
import it.lorenzomalla.app.constants.Constant.Endpoint;
import it.lorenzomalla.app.model.Vehicle;
import it.lorenzomalla.app.service.VehicleService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(Endpoint.USER)
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

	@Override
	public ResponseEntity<List<Vehicle>> listOfVehicle() {
		return ResponseEntity.ok().body(vehicleService.getListOfVehicle());
	}

	@Override
	public ResponseEntity<Vehicle> retrieveVehicle(String vehicleId) {
		return ResponseEntity.ok().body(vehicleService.getVehicleById(vehicleId));
	}

}
