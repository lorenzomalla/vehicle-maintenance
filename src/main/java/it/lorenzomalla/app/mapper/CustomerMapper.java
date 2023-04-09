package it.lorenzomalla.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	Customer fromEntity(CustomerEntity customerEntity);
	
	List<Customer> fromListEntity(List<CustomerEntity> listCustomerEntity);

}
