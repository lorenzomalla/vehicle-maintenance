package it.lorenzomalla.app.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.lorenzomalla.app.constants.Constant.ErrorCode;
import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.mapper.CustomerMapper;
import it.lorenzomalla.app.model.Customer;
import it.lorenzomalla.app.repository.CustomerRepository;
import it.lorenzomalla.app.service.CustomerService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	@Override
	public Customer getCustomerDetails(String customerId) {
		CustomerEntity customerEntity = customerRepository.findById(UUID.fromString(customerId))
				.orElseThrow(() -> new VehicleRuntimeException(ErrorCode._404, "Customer not found", HttpStatus.NOT_FOUND));
		return customerMapper.fromEntity(customerEntity);
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<CustomerEntity> customerEntities = customerRepository.findAll();
		return customerMapper.fromListEntity(customerEntities);
	}

}
