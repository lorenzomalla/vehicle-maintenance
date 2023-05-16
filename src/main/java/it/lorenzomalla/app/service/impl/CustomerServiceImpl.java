package it.lorenzomalla.app.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import it.lorenzomalla.app.constants.Constant.ErrorCode;
import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.entity.VehicleEntity;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.mapper.CustomerMapper;
import it.lorenzomalla.app.mapper.VehicleMapper;
import it.lorenzomalla.app.model.Customer;
import it.lorenzomalla.app.model.Vehicle;
import it.lorenzomalla.app.repository.CustomerRepository;
import it.lorenzomalla.app.service.CustomerService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	private final VehicleMapper vehicleMapper;

	@Override
	public Customer getCustomerDetails(String customerId) {
		CustomerEntity customerEntity = customerRepository.findById(UUID.fromString(customerId)).orElseThrow(
				() -> new VehicleRuntimeException(ErrorCode._404, "Customer not found", HttpStatus.NOT_FOUND));
		Customer customer = new Customer();
		customerMapper.fromEntity(customerEntity, customer);

		List<Vehicle> listVehicle = vehicleMapper.fromEntites(customerEntity.getVehicles());
		customer.setVehicles(listVehicle);
		return customer;
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<CustomerEntity> customerEntities = customerRepository.findAll();
		List<Customer> listCustomerResponse = new ArrayList<>();

		customerEntities.forEach(customer -> {
			List<Vehicle> listVehicle = Collections.emptyList();
			if (!CollectionUtils.isEmpty(customer.getVehicles())) {
				listVehicle = customer.getVehicles().stream().map(vehicleMapper::fromEntity)
						.collect(Collectors.toList());
			}
			listCustomerResponse.add(customerMapper.fromEntity(customer, Customer.builder().vehicles(listVehicle).build()));
		});

		customerEntities.stream().map(customer -> mappingVehiclesOfCustomer(customer.getVehicles()))
				.collect(Collectors.toList());

		return listCustomerResponse;
	}

	@Override
	public Customer getVehiclesByCustomerId(String customerId) {
		Optional<CustomerEntity> customerEntity = customerRepository.findById(UUID.fromString(customerId));
		Customer customer = new Customer();
		List<Vehicle> listVehicle = vehicleMapper.fromEntites(customerEntity.get().getVehicles());
		customer.setVehicles(listVehicle);
		return customer;
	}

	private List<Vehicle> mappingVehiclesOfCustomer(List<VehicleEntity> vehicles) {
		return vehicleMapper.fromEntites(vehicles);
	}

}
