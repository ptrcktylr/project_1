package com.revature.services;

import com.revature.dao.UserDao;
import com.revature.models.User;

public class LoginService {

	public User userLogin(String username, String password) {
		UserDao uD = new UserDao();
		return uD.validLoginByRole(username, password);
	}

}