package it.lorenzomalla.event.mapper;

import org.mapstruct.Mapper;

import it.lorenzomalla.event.entity.InviteEntity;
import it.lorenzomalla.event.pojo.InvitePojo;

@Mapper(componentModel = "spring")
public interface InviteMapper {

	InviteEntity map(InvitePojo pojo);

}
