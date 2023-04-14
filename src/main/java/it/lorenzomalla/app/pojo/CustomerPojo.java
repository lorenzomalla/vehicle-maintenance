package it.lorenzomalla.app.pojo;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerPojo {
	
	private UUID id;
	private String username;
	private String email;
	private String phoneNumber;
	private String role;

}
