package it.lorenzomalla.app.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.entity.RoleEntity;
import it.lorenzomalla.app.entity.enumaration.ERole;
import it.lorenzomalla.app.model.Customer;
import it.lorenzomalla.app.pojo.CustomerPojo;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

	@Mapping(target = "vehicles", ignore = true)
	@Mapping(target = "name", source = "username")
	public abstract Customer fromEntity(CustomerEntity customerEntity, @MappingTarget Customer customer);

	@Mapping(target = "role", expression = "java(mapRole(customerEntity.getRoles()))")
	public abstract CustomerPojo fromEntityToPojo(CustomerEntity customerEntity);

	String mapRole(Set<RoleEntity> roles) {
		return roles.stream().map(RoleEntity::getName).map(ERole::name).findFirst().orElse(null);
	}

}
