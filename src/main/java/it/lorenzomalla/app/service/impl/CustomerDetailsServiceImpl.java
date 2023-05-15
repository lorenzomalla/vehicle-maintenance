package it.lorenzomalla.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.lorenzomalla.app.constants.Constant.ErrorCode;
import it.lorenzomalla.app.entity.CustomerEntity;
import it.lorenzomalla.app.exception.VehicleRuntimeException;
import it.lorenzomalla.app.repository.CustomerRepository;

@Service
public class CustomerDetailsServiceImpl implements UserDetailsService {

	@Autowired
	CustomerRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomerEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new VehicleRuntimeException(ErrorCode._404,
						"User Not Found with username: " + username, HttpStatus.NOT_FOUND));

		return CustomerDetailsImpl.build(user);
	}
}