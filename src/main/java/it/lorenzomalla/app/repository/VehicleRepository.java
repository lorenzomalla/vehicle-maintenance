package it.lorenzomalla.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.lorenzomalla.app.entity.VehicleEntity;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {

	VehicleEntity findByIdAndCustomerId(String vehicleId, String customerId);

}