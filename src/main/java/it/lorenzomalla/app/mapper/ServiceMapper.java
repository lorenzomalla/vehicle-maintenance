package it.lorenzomalla.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.lorenzomalla.app.entity.ServiceEntity;
import it.lorenzomalla.app.model.Service;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

	@Mapping(target = "id", ignore = true)
	ServiceEntity fromRequest(Service request);

	List<Service> fromEntites(List<ServiceEntity> entites);

	Service fromEntity(ServiceEntity entity);

}
