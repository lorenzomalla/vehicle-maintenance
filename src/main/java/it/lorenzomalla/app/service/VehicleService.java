package it.lorenzomalla.app.service;

import java.util.List;

import it.lorenzomalla.app.model.Vehicle;

public interface VehicleService {

	Vehicle createVehicle(String customerId, Vehicle body);

	Vehicle updateVehicle(String idVehicle, Vehicle body);
	
	List<Vehicle> getListOfVehicle();
	
	Vehicle getVehicleById(String vehicleId);

}
