package it.lorenzomalla.event.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.event.api.SignupApi;
import it.lorenzomalla.event.model.Login;

@RestController
public class SignupController implements SignupApi {

	@Override
	public ResponseEntity<Void> signup(@Valid Login body) {
		// TODO Auto-generated method stub
		return SignupApi.super.signup(body);
	}
}
