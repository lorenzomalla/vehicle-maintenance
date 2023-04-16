package it.lorenzomalla.app.service;

import it.lorenzomalla.app.model.JWTResponse;
import it.lorenzomalla.app.model.LoginRequest;
import it.lorenzomalla.app.model.SignupRequest;
import it.lorenzomalla.app.model.UserInfoResponse;

public interface AuthService {

	JWTResponse login(LoginRequest body);

	UserInfoResponse signup(SignupRequest body);

}
