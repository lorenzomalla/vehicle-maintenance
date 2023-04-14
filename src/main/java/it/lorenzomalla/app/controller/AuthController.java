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

import it.lorenzomalla.app.configuration.security.JwtProvider;
import it.lorenzomalla.app.constants.Constant.General;
import it.lorenzomalla.app.constants.Constant.Role;
import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.entity.RoleEntity;
import it.lorenzomalla.app.entity.enumaration.ERole;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.mapper.CustomerMapper;
import it.lorenzomalla.app.model.LoginRequest;
import it.lorenzomalla.app.pojo.CustomerPojo;
import it.lorenzomalla.app.repository.CustomerRepository;
import it.lorenzomalla.app.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("public/authentication")
@Slf4j
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomerRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	CustomerMapper customerMapper;

	@PostMapping("/signin")
	public ResponseEntity<Map<String, String>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		CustomerEntity user = this.userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new VehicleRuntimeException("404", "Cliente non trovato", HttpStatus.NOT_FOUND));
		if (!this.encoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
		}
		ObjectMapper om = new ObjectMapper();
		om.registerModules(new JavaTimeModule());
		CustomerPojo userNode = customerMapper.fromEntityToPojo(user);
		Map<String, Object> claimMap = new HashMap<String, Object>();
		try {
			claimMap.put(General.CUSTOMER, om.writeValueAsString(userNode));
		} catch (JsonProcessingException e) {
			log.error("Error during parse CustomerEntity in Signing");
		}

		Map<String, String> map = new HashMap<>();
		map.put("jwt", JwtProvider.createJwt(loginRequest.getEmail(), claimMap));
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody LoginRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().build();
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().build();
		}

		CustomerEntity user = CustomerEntity.builder().username(signUpRequest.getUsername())
				.email(signUpRequest.getEmail()).password(encoder.encode(signUpRequest.getPassword())).build();

		List<String> strRoles = signUpRequest.getRoles();
		Set<RoleEntity> roles = new HashSet<>();

		if (strRoles == null) {
			findRole(roles, ERole.USER);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case Role.USER:
					findRole(roles, ERole.ADMIN);
					break;
				default:
					findRole(roles, ERole.USER);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok().build();
	}

	private void findRole(Set<RoleEntity> roles, ERole erole) {
		RoleEntity adminRole = roleRepository.findByName(erole)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(adminRole);
	}
}