package it.lorenzomalla.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.api.AuthenticationApi;
import it.lorenzomalla.app.model.JWTResponse;
import it.lorenzomalla.app.model.LoginRequest;
import it.lorenzomalla.app.model.SignupRequest;
import it.lorenzomalla.app.model.UserInfoResponse;
import it.lorenzomalla.app.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping("public")
public class AuthController implements AuthenticationApi {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuthService authService;

	@Override
	public ResponseEntity<JWTResponse> login(@Valid LoginRequest body) {
		return ResponseEntity.ok().body(authService.login(body));
	}

	@Override
	public ResponseEntity<Void> signout() {
		return null;
	}

	@Override
	public ResponseEntity<UserInfoResponse> signup(@Valid SignupRequest body) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(body));
	}

}