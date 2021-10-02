package com.revature.controllers;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.services.LoginService;
import com.revature.utils.JwtUtil;

import io.javalin.http.Handler;

public class LoginController {

	public static HttpSession sessionUser = null;

	public Handler loginHandler = (ctx) -> {

		LoginService ls = new LoginService();
		String body = ctx.body();
		Logger log = LogManager.getLogger(LoginController.class);

		Gson gson = new Gson();

		LoginDTO LDTO = gson.fromJson(body, LoginDTO.class);

		//control flow for successful login

		//invoke the login() method of LoginService using the username and password in the newly created LoginDTO

		if(LDTO == null) {
			ctx.status(401); //unauthorized status code
			ctx.result("Login Field Empty");
			return;
		}

		User user =  ls.userLogin(LDTO.getUsername(), LDTO.getPassword());

		if(user != null) {
			//generate a JSON Web Token to uniquely identify the user
			@SuppressWarnings("unused")
			String jwt = JwtUtil.generate(LDTO.getUsername(), LDTO.getPassword());

			//create a user session
			sessionUser = ctx.req.getSession();
			sessionUser.setAttribute("user", user);

			String JSONUser = gson.toJson(user);
			ctx.result(JSONUser);
			
			// successful status code
			ctx.status(200);
			
			// TODO: log this instead
			System.out.println(sessionUser.getAttribute("user"));
			log.info("USER LOGGIN ACCOUNT SUCCESS: " + user.getUsername() + " - Role id: " + user.getRole_id());

		} else {
			ctx.status(401); //unauthorized status code
			ctx.result("Login Failed");
			log.error("USER LOGGIN ACCOUNT FAILED");
		}



	};
	public Handler logoutHandler = (ctx) -> {
		Logger log = LogManager.getLogger(LoginController.class);
		if(ctx.req.getSession(false) != null) {
			User user = (User) LoginController.sessionUser.getAttribute("user");
			log.info("USER LOGGIN ACCOUNT SUCCESS: " + user.getUsername() + " - Role id: " + user.getRole_id());
			sessionUser.invalidate();
			ctx.result("Logged Out");
		} else {
			ctx.result("You are already Logged Out");
		}

	};

} 