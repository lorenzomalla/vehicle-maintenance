package it.lorenzomalla.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.api.CustomersApi;
import it.lorenzomalla.app.model.Customer;
import it.lorenzomalla.app.service.CustomerService;
import lombok.AllArgsConstructor;

@Controller
@RestController("/api")
@AllArgsConstructor
public class CustomerController implements CustomersApi {

	private final CustomerService customerService;

	@Override
	public ResponseEntity<List<Customer>> getAllCustomers() {

		return CustomersApi.super.getAllCustomers();
	}

	@Override
	public ResponseEntity<Customer> getCustomerDetails(String customerId) {
		return ResponseEntity.ok().body(customerService.getCustomerDetails(customerId));
	}

}
