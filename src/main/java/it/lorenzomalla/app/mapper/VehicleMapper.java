package it.lorenzomalla.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.lorenzomalla.app.entity.VehicleEntity;
import it.lorenzomalla.app.model.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

	@Mapping(target = "customer", ignore = true)
	@Mapping(target = "interventions", ignore = true)
	@Mapping(target = "registerDate", expression = "java(java.time.LocalDateTime.now())")
	VehicleEntity fromRequest(Vehicle request);
	
	List<Vehicle> fromEntites(List<VehicleEntity> entites);
	
	@Mapping(target = "customer", ignore = true)
	Vehicle fromEntity(VehicleEntity entity);
	
}
