package it.lorenzomalla.app.constants;

public interface Constant {

	interface Role {
		String ADMIN = "ADMIN";
		String USER = "USER";
	}
	
	interface General{
		String CUSTOMER = "CUSTOMER";
	}
	
	interface ErrorCode {
		String _404 = "404";
		String _400 = "400";
		String _500 = "500";
		String _401 = "401";
		String _403 = "403";
	}
	
	interface Endpoint {
		String USER = "/v1/api/user";
		String ADMIN = "/v1/api/admin";
		String PUBLIC = "/public";
	}
	
	interface ROLE_AUTH {
		String ADMIN_AUTH = "hasAnyAuthority('ADMIN')";
	}
}
