package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDaoInterface {
	
	// get all users
	public List<User> getAllUsers();
	
	// get user by id
	public User getUserById(int user_id);
	
	// get users by role id
	public List<User> getUsersByRoleId(int role_id);
	
	// validate user, return user object
	public User validLoginByRole(String username, String password);
	
}
