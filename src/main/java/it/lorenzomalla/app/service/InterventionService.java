package it.lorenzomalla.app.service;

import java.util.List;

import it.lorenzomalla.app.model.Intervention;

public interface InterventionService {

	Intervention createNewIntervention(String customerId, String vehicleId, Intervention intervention);

	Void deleteIntervention(String interventionId);

	Intervention updateIntervention(String interventionId, Intervention intervention);
	
	List<Intervention> getListIntervention();

}
