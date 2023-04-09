package it.lorenzomalla.app.service.impl;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.entity.VehicleEntity;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.mapper.VehicleMapper;
import it.lorenzomalla.app.model.Vehicle;
import it.lorenzomalla.app.repository.CustomerRepository;
import it.lorenzomalla.app.repository.VehicleRepository;
import it.lorenzomalla.app.service.VehicleService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

	private final VehicleMapper vehicleMapper;
	private final VehicleRepository vehicleRepository;
	private final CustomerRepository customerRepository;

	@Override
	public Vehicle createVehicle(String customerId, Vehicle body) {
		VehicleEntity vehicleEntity = vehicleMapper.fromRequest(body);
		vehicleEntity.setCustomer(customerRepository.findById(UUID.fromString(customerId)).orElse(new CustomerEntity()));
		vehicleRepository.save(vehicleEntity);
		body.setId(vehicleEntity.getId());
		return body;
	}

	@Override
	public Vehicle updateVehicle(String idVehicle, Vehicle body) {
		VehicleEntity vehicleEntity = vehicleRepository.findById(UUID.fromString(idVehicle))
				.orElseThrow(() -> new VehicleRuntimeException("404", "Nessuno veicolo trovato da aggiornare",
						HttpStatus.NOT_FOUND));
		vehicleEntity = vehicleMapper.fromRequest(body);
		vehicleRepository.save(vehicleEntity);
		return body;
	}

}
