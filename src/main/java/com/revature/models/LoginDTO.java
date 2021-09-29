package com.revature.models;

//Data Transfer Object
//A model of /some/data coming from the client
//An HTTP handler will parse a JSON object sent by the user, containing their username and password
//this then gets sent to the controller to get turned into a Java object
//The username and passwor dwill be put into the DTO as fields, which will get checked/validated by the service layer
//You NEVER store a DTO in a database. It's purely for Data Transfer... hence Data Transfer Object

public class LoginDTO {

	//our LoginDTO models only the username/password of our users
	private String username;
	private String password;
	private int user_role_id;

	@Override
	public String toString() {
		return "LoginDTO [username=" + username + ", password=" + password + ", user_role_id=" + user_role_id + "]";
	}

	//Then I just want two constructors so we can instantiate this class when needed
	public LoginDTO() {
		super();
	}

	public LoginDTO(String username, String password, int user_role_id) {
		super();
		this.username = username;
		this.password = password;
		this.user_role_id = user_role_id;
	}

	public int getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}





} 