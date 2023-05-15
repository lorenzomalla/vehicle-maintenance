package it.lorenzomalla.app.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.lorenzomalla.app.constants.Constant.ErrorCode;
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
		CustomerEntity customerEntity = customerRepository.findById(UUID.fromString(customerId))
				.orElse(new CustomerEntity());
		vehicleEntity.setCustomer(customerEntity);
		vehicleRepository.save(vehicleEntity);
		body.setId(vehicleEntity.getId());
		return body;
	}

	@Override
	public Vehicle updateVehicle(String idVehicle, Vehicle body) {
		VehicleEntity vehicleEntity = vehicleRepository.findById(UUID.fromString(idVehicle))
				.orElseThrow(() -> new VehicleRuntimeException(ErrorCode._404, "No vehicle found to update",
						HttpStatus.NOT_FOUND));
		vehicleEntity = vehicleMapper.fromRequest(body);
		vehicleRepository.save(vehicleEntity);
		return body;
	}

	@Override
	public List<Vehicle> getListOfVehicle() {
		List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();
		return vehicleMapper.fromEntites(vehicleEntities);
	}

	@Override
	public Vehicle getVehicleById(String vehicleId) {
		VehicleEntity vehicleEntity = vehicleRepository.findById(UUID.fromString(vehicleId))
				.orElseThrow(() -> new VehicleRuntimeException(ErrorCode._404, "Vehicle not found", HttpStatus.NOT_FOUND));
		return vehicleMapper.fromEntity(vehicleEntity);
	}

}
