package it.lorenzomalla.app.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzomalla.app.configuration.security.JwtUtils;
import it.lorenzomalla.app.entity.RoleEntity;
import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.entity.enumaration.ERole;
import it.lorenzomalla.app.model.LoginRequest;
import it.lorenzomalla.app.model.UserInfoResponse;
import it.lorenzomalla.app.repository.RoleRepository;
import it.lorenzomalla.app.repository.CustomerRepository;
import it.lorenzomalla.app.service.impl.CustomerDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
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
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		CustomerDetailsImpl userDetails = (CustomerDetailsImpl) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(UserInfoResponse.builder().email(userDetails.getEmail()).username(userDetails.getUsername())
						.id(userDetails.getId()).roles(roles).build());
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody LoginRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().build();
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().build();
		}

		CustomerEntity user = CustomerEntity.builder().username(signUpRequest.getUsername()).email(signUpRequest.getEmail())
				.password(encoder.encode(signUpRequest.getPassword())).build();

		List<String> strRoles = signUpRequest.getRoles();
		Set<RoleEntity> roles = new HashSet<>();

		if (strRoles == null) {
			findRole(roles, ERole.ROLE_USER);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					findRole(roles, ERole.ROLE_ADMIN);
					break;
				case "mod":
					findRole(roles, ERole.ROLE_MODERATOR);
					break;
				default:
					findRole(roles, ERole.ROLE_USER);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
	}

	private void findRole(Set<RoleEntity> roles, ERole erole) {
		RoleEntity adminRole = roleRepository.findByName(erole)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(adminRole);
	}
}