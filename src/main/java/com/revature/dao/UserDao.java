package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        try(Connection conn = ConnectionUtil.getConnection()){
            
            ResultSet rs = null;
            
            String sql = "select * from ers_users where ers_user_id = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, user_id);
            
            rs = ps.executeQuery();
            
            User user_reim = null;
            rs.next();
            user_reim = new User(
                    rs.getInt("ers_user_id"),
                    rs.getString("ers_username"),
                    rs.getString("ers_password").toString(),
                    rs.getString("user_first_name").toString(),
                    rs.getString("user_last_name"),
                    rs.getString("user_email"),
                    rs.getInt("user_role_id"));
            
            return user_reim;
            
            
        } catch(SQLException e) {
            System.out.println("Something went wrong with your checkusername database");
            e.printStackTrace();
        }
        return null;
	}

	@Override
	public List<User> getUsersByRoleId(int role_id) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			// query db
			String sql = "SELECT * FROM ers_users WHERE user_role_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, role_id);
			ResultSet rs = ps.executeQuery();
			
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
	public User validLoginByRole(String username, String password, int user_role_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			// query db
			String sql = "SELECT * FROM ers_users WHERE ers_username = ? and ers_password = ? and user_role_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, user_role_id);
			ResultSet rs = ps.executeQuery();

			// put all users into list
			if(rs.next()) {

				User user = new User(
						rs.getInt("ers_user_id"),
						rs.getString("ers_username"),
						rs.getString("ers_password"),
						rs.getString("user_first_name"),
						rs.getString("user_last_name"),
						rs.getString("user_email"),
						rs.getInt("user_role_id")
						);

				return user;
			}

			return null;


		} catch (SQLException e) {
			System.out.println("Failed to get all users");
			e.printStackTrace();
		}

		return null;
	}
	
}
