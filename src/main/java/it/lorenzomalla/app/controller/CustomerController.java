package it.lorenzomalla.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.api.CustomersApi;
import it.lorenzomalla.app.constants.Constant.Endpoint;
import it.lorenzomalla.app.model.Customer;
import it.lorenzomalla.app.service.CustomerService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(Endpoint.ADMIN)
@AllArgsConstructor
public class CustomerController implements CustomersApi {

	private final CustomerService customerService;

	@Override
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok().body(customerService.getAllCustomers());
	}

	@Override
	public ResponseEntity<Customer> getCustomerDetails(String customerId) {
		return ResponseEntity.ok().body(customerService.getCustomerDetails(customerId));
	}

	@Override
	public ResponseEntity<Customer> getVehicleSpecificCustomer(String customerId) {
		return ResponseEntity.ok().body(customerService.getVehiclesByCustomerId(customerId));
	}

}
