package it.lorenzomalla.event.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lorenzomalla.event.entity.InviteEntity;
import it.lorenzomalla.event.mapper.InviteMapper;
import it.lorenzomalla.event.model.Invite;
import it.lorenzomalla.event.pojo.InvitePojo;
import it.lorenzomalla.event.repository.InviteJpaConnector;
import it.lorenzomalla.event.service.InviteService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InviteServiceImpl implements InviteService {

	@Autowired
	private final InviteJpaConnector inviteJpaConnector;

	private final InviteMapper inviteMapper;

	@Override
	public Invite createEvent(InvitePojo event) {
		InviteEntity inviteEntity = inviteMapper.map(event);
		inviteJpaConnector.save(inviteEntity);
		return new Invite();
	}

}
