package it.lorenzomalla.app.service;

import java.util.List;

import it.lorenzomalla.app.model.Customer;

public interface CustomerService {

	Customer getCustomerDetails(String customerId);

	List<Customer> getAllCustomers();
	
	Customer getVehiclesByCustomerId(String customerId);

}
