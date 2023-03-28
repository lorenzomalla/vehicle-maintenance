package it.lorenzomalla.event.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.event.api.EventApi;
import it.lorenzomalla.event.model.Invite;
import it.lorenzomalla.event.pojo.InvitePojo;
import it.lorenzomalla.event.service.InviteService;

@RestController
public class EventController implements EventApi {

	@Autowired
	private InviteService inviteService;

	@Override
	public ResponseEntity<Void> createInvite(String eventId, @Valid Invite body) {
		InvitePojo invitePojo = InvitePojo.builder().email(body.getEmail()).name(body.getName()).build();
		inviteService.createEvent(invitePojo);
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	@Override
	public ResponseEntity<Void> retrieveSpecificInvite(String eventId, String inviteId) {
		
		return null;
	}
}
