package it.lorenzomalla.app.service.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.lorenzomalla.app.entity.InterventionEntity;
import it.lorenzomalla.app.entity.VehicleEntity;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.mapper.InterventionMapper;
import it.lorenzomalla.app.model.Intervention;
import it.lorenzomalla.app.repository.InterventionRepository;
import it.lorenzomalla.app.repository.VehicleRepository;
import it.lorenzomalla.app.service.InterventionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class InterventionServiceImpl implements InterventionService {

	private final InterventionMapper interventionMapper;
	private final InterventionRepository interventionRepository;
	private final VehicleRepository vehicleRepository;

	@Override
	public Intervention createNewIntervention(String customerId, String vehicleId, Intervention intervention) {
		log.info("CustomerId[{}] VehicleId[{}] InterventionBody {} ", customerId, vehicleId, intervention);
		VehicleEntity vehicleEntity = vehicleRepository.findById(UUID.fromString(vehicleId)).orElse(null);
		InterventionEntity interventionEntity = interventionMapper.fromRequest(intervention);
		interventionEntity.setVehicle(vehicleEntity);

		interventionEntity = interventionRepository.save(interventionEntity);
		intervention.setId(interventionEntity.getId());
		return intervention;
	}

	@Override
	@Transactional
	public Void deleteIntervention(String interventionId) {
		log.info("Delete Intervention with Id [{}]", interventionId);
		interventionRepository.deleteById(UUID.fromString(interventionId));
		return null;
	}

	@Override
	public Intervention updateIntervention(String interventionId, Intervention intervention) {
		log.info("Update Intervention with Id [{}] and Body {} ", interventionId, intervention);
		InterventionEntity interventionEntity = interventionRepository.findById(UUID.fromString(interventionId))
				.orElseThrow(() -> new VehicleRuntimeException("404", "Non è stato trovato nessun interveto",
						HttpStatus.NOT_FOUND));
		interventionEntity = interventionMapper.fromUpdateRequest(interventionEntity, intervention);

		interventionRepository.save(interventionEntity);
		return intervention;
	}

}
