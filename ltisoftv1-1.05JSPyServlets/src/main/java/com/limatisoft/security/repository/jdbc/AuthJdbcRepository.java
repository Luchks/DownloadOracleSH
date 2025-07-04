package com.limatisoft.security.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.limatisoft.db.DatabaseConnectionManager;
import com.limatisoft.security.model.User;
import com.limatisoft.security.repository.AuthRepository;

public class AuthJdbcRepository implements AuthRepository{

	@Override
	public User findByLoginAndPassword(String login, String password) {
		try (
			Connection connection = 
				DatabaseConnectionManager.getConnection(); 
			) {
			 
			PreparedStatement ps = connection.prepareStatement(""" 
					SELECT ba_user_id, name, login_user, password_user, is_active
					FROM ba_user 
					WHERE lower(login_user) = ? AND password_user = ?;
					""");
			ps.setString(1, login.toLowerCase());
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getLong("ba_user_id"));
				user.setName(rs.getString("name"));
				user.setLogin(rs.getString("login_user"));
				user.setPassword(rs.getString("password_user"));
				user.setIsActive(rs.getBoolean("is_active"));
				return user;
			}
			return null;
		}catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

}
