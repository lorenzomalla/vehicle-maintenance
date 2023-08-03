package it.lorenzomalla.app.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.lorenzomalla.app.entity.InterventionEntity;
import it.lorenzomalla.app.entity.VehicleEntity;
import it.lorenzomalla.app.model.Intervention;

@Mapper(componentModel = "spring")
public interface InterventionMapper {

	@Mapping(target = "vehicle", ignore = true)
	InterventionEntity fromRequest(Intervention body);

	@Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
	InterventionEntity fromUpdateRequest(@MappingTarget InterventionEntity entity, Intervention body);

	List<Intervention> fromEntities(List<InterventionEntity> listEntities);

	@Mapping(target = "idVehicle", expression = "java(mapIdVehicle(entity.getVehicle()))")
	Intervention fromEntity(InterventionEntity entity);

	default UUID mapIdVehicle(VehicleEntity vehicle) {
		return vehicle.getId();
	}

}
