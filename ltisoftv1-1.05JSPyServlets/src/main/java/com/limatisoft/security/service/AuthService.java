package com.limatisoft.security.service;

import com.limatisoft.exceptions.AuthException;
import com.limatisoft.security.model.User;
import com.limatisoft.security.repository.AuthRepository;
import com.limatisoft.security.repository.jdbc.AuthJdbcRepository;

public class AuthService {
	private AuthRepository authRepository = new AuthJdbcRepository();
 
	public User authenticate(String login, String password) {
		User user = authRepository.findByLoginAndPassword(login, password);
		assertUserValid(user);
		return user;
	}
	
	private void assertUserValid(User user) {
		if(user == null) {
			throw new AuthException("Login y password Incorrecto");
		}
		if(user.getIsActive().equals(false)) {
			throw new AuthException("Usuario inactivo");
		}
	}
}
