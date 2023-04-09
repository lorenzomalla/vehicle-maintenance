package it.lorenzomalla.app.service;

import it.lorenzomalla.app.model.Vehicle;

public interface VehicleService {

	Vehicle createVehicle(String customerId, Vehicle body);

	Vehicle updateVehicle(String idVehicle, Vehicle body);

}
