package it.lorenzomalla.app.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.lorenzomalla.app.constants.Constant.ErrorCode;
import it.lorenzomalla.app.entity.ServiceEntity;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.mapper.ServiceMapper;
import it.lorenzomalla.app.repository.ServiceRepository;
import it.lorenzomalla.app.service.ServiceInterventionService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceInterventionServiceImpl implements ServiceInterventionService {

	private final ServiceRepository serviceRepository;
	private final ServiceMapper serviceMapper;

	@Override
	public List<it.lorenzomalla.app.model.Service> getAllServices() {
		List<ServiceEntity> serviceEntities = serviceRepository.findAll();
		return serviceMapper.fromEntites(serviceEntities);
	}

	@Override
	public it.lorenzomalla.app.model.Service getServiceById(String serviceId) {
		ServiceEntity serviceEntity = serviceRepository.findById(UUID.fromString(serviceId)).orElseThrow(
				() -> new VehicleRuntimeException(ErrorCode._404, "Service not found", HttpStatus.NOT_FOUND));
		return serviceMapper.fromEntity(serviceEntity);
	}

	@Override
	@Transactional
	public void deleteServiceById(String serviceId) {
		serviceRepository.deleteById(UUID.fromString(serviceId));
	}

	@Override
	@Transactional
	public it.lorenzomalla.app.model.Service createService(it.lorenzomalla.app.model.Service body) {
		ServiceEntity serviceEntity = serviceMapper.fromRequest(body);
		serviceRepository.save(serviceEntity);
		body.setId(serviceEntity.getId());
		return body;
	}

}
