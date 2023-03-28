package it.lorenzomalla.event.service;

import it.lorenzomalla.event.model.Invite;
import it.lorenzomalla.event.pojo.InvitePojo;

public interface InviteService {
	
	Invite createEvent(InvitePojo event);
	

}
