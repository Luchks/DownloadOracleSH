package com.limatisoft.security.repository;

import com.limatisoft.security.model.User;

public interface AuthRepository {
	public User findByLoginAndPassword(String login, String password);
}
