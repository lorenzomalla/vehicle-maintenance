package it.lorenzomalla.app.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import it.lorenzomalla.app.model.JWTResponse;
import it.lorenzomalla.app.model.LoginRequest;
import it.lorenzomalla.app.model.SignupRequest;
import it.lorenzomalla.app.model.UserInfoResponse;
import it.lorenzomalla.app.pojo.CustomerPojo;
import it.lorenzomalla.app.repository.CustomerRepository;
import it.lorenzomalla.app.repository.RoleRepository;
import it.lorenzomalla.app.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public JWTResponse login(LoginRequest body) {
		CustomerEntity user = customerRepository.findByEmail(body.getEmail())
				.orElseThrow(() -> new VehicleRuntimeException("404", "Cliente non trovato", HttpStatus.NOT_FOUND));
		if (!encoder.matches(body.getPassword(), user.getPassword())) {
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

		return JWTResponse.builder().jwt(JwtProvider.createJwt(body.getEmail(), claimMap)).build();
	}

	@Override
	public UserInfoResponse signup(SignupRequest body) {
		if (customerRepository.existsByUsername(body.getUsername())) {
			throw new VehicleRuntimeException("400", "Esiste già un utente con questo username",
					HttpStatus.BAD_REQUEST);
		}

		if (customerRepository.existsByEmail(body.getEmail())) {
			throw new VehicleRuntimeException("400", "Esiste già un utente con questa email", HttpStatus.BAD_REQUEST);
		}

		CustomerEntity customerEntity = CustomerEntity.builder().username(body.getUsername()).email(body.getEmail())
				.password(encoder.encode(body.getPassword())).build();

		List<String> strRoles = body.getRoles();
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
		customerEntity.setRoles(roles);
		customerRepository.save(customerEntity);
		body.setId(customerEntity.getId());
		return null;
	}

	private void findRole(Set<RoleEntity> roles, ERole erole) {
		RoleEntity adminRole = roleRepository.findByName(erole)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(adminRole);
	}

}
