package it.lorenzomalla.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.lorenzomalla.app.entity.InterventionEntity;

@Repository
public interface InterventionRepository extends JpaRepository<InterventionEntity, UUID> {

}