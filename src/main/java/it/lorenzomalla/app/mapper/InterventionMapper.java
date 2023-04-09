package it.lorenzomalla.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.lorenzomalla.app.entity.InterventionEntity;
import it.lorenzomalla.app.model.Intervention;

@Mapper(componentModel = "spring")
public interface InterventionMapper {

	@Mapping(target = "vehicle", ignore = true)
	InterventionEntity fromRequest(Intervention body);
	
	@Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
	InterventionEntity fromUpdateRequest(@MappingTarget InterventionEntity entity, Intervention body);

}
