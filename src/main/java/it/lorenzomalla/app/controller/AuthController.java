package it.lorenzomalla.app.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.lorenzomalla.app.api.AuthenticationApi;
import it.lorenzomalla.app.configuration.security.JwtProvider;
import it.lorenzomalla.app.constants.Constant.General;
import it.lorenzomalla.app.constants.Constant.Role;
import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.entity.RoleEntity;
import it.lorenzomalla.app.entity.enumaration.ERole;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.mapper.CustomerMapper;
import it.lorenzomalla.app.model.JWTResponse;
import it.lorenzomalla.app.model.LoginRequest;
import it.lorenzomalla.app.model.SignupRequest;
import it.lorenzomalla.app.model.UserInfoResponse;
import it.lorenzomalla.app.pojo.CustomerPojo;
import it.lorenzomalla.app.repository.CustomerRepository;
import it.lorenzomalla.app.repository.RoleRepository;
import it.lorenzomalla.app.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("public")
@Slf4j
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
		// TODO Auto-generated method stub
		return AuthenticationApi.super.signout();
	}

	@Override
	public ResponseEntity<UserInfoResponse> signup(@Valid SignupRequest body) {
		return ResponseEntity.ok().body(authService.signup(body));
	}


}