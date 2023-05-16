package it.lorenzomalla.app.service;

import java.util.List;

import it.lorenzomalla.app.model.Service;

public interface ServiceInterventionService {
	
	List<Service> getAllServices();
	
	Service getServiceById(String serviceId);
	
	void deleteServiceById(String serviceId);
	
	Service createService(Service body);

}
