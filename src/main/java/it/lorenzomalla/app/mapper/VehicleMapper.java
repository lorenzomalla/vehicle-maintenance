package it.lorenzomalla.app.mapper;

import org.mapstruct.Mapper;

import it.lorenzomalla.app.entity.VehicleEntity;
import it.lorenzomalla.app.model.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

	VehicleEntity fromRequest(Vehicle request);

}
