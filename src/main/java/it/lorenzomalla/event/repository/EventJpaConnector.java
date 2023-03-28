package it.lorenzomalla.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lorenzomalla.event.entity.EventEntity;

public interface EventJpaConnector extends JpaRepository<EventEntity, Long>{

}
