package it.lorenzomalla.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.api.ServiceApi;
import it.lorenzomalla.app.constants.Constant.Endpoint;
import it.lorenzomalla.app.constants.Constant.ROLE_AUTH;
import it.lorenzomalla.app.model.Service;
import it.lorenzomalla.app.service.ServiceInterventionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(Endpoint.USER)
public class ServiceController implements ServiceApi {
	
	private final ServiceInterventionService interventionService;

	@Override
	@PreAuthorize(ROLE_AUTH.ADMIN_AUTH)
	public ResponseEntity<Service> createService(@Valid Service service) {
		return ResponseEntity.ok().body(interventionService.createService(service));
	}

	@Override
	@PreAuthorize(ROLE_AUTH.ADMIN_AUTH)
	public ResponseEntity<Void> deleteServiceById(String idService) {
		interventionService.deleteServiceById(idService);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<List<Service>> getAllServices() {
		return ResponseEntity.ok().body(interventionService.getAllServices());
	}

	@Override
	public ResponseEntity<Service> getServiceById(String idService) {
		return ResponseEntity.ok().body(interventionService.getServiceById(idService));
	}

}
