package it.lorenzomalla.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lorenzomalla.event.entity.InviteEntity;

public interface InviteJpaConnector extends JpaRepository<InviteEntity, Long>{

}
