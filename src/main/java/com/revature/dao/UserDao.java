package com.revature.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDao implements UserDaoInterface {

	@Override
	public List<User> getAllUsers() {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			// query db
			String sql = "SELECT * FROM ers_users";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			// create new list 
			List<User> userList = new ArrayList<User>();
			
			// put all users into list
			while (rs.next()) {
				
				// create new user object
				User user = new User (
						rs.getInt("ers_user_id"),
						rs.getString("ers_username"),
						rs.getString("ers_password"),
						rs.getString("user_first_name"),
						rs.getString("user_last_name"),
						rs.getString("user_email"),
						rs.getInt("user_role_id")
						);
				
				// add object to list
				userList.add(user);
				
			}
			
			return userList;
			
			
		} catch (SQLException e) {
			System.out.println("Failed to get all users");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public User getUserById(int user_id) {
		return null;
	}

	@Override
	public List<User> getUsersByRoleId(int role_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
