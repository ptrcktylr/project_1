package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() throws SQLException {
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// hardcoded DB info
		String url = "jdbc:postgresql://34.135.152.149:5432/postgres?currentSchema=er_system";
		String username = "postgres";
		String password = "password";
		
		return DriverManager.getConnection(url, username, password);
	}
	
}
