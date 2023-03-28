package it.lorenzomalla.event.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.event.api.LoginApi;
import it.lorenzomalla.event.model.Login;

@RestController
public class LoginController implements LoginApi {

	@Override
	public ResponseEntity<Void> login(@Valid Login body) {
		// TODO Auto-generated method stub
		return LoginApi.super.login(body);
	}
}
