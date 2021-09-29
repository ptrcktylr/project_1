package com.revature.controllers;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.services.LoginService;
import com.revature.utils.JwtUtil;

import io.javalin.http.Handler;

public class LoginController {

	static HttpSession sessionUser = null;

	public Handler loginHandler = (ctx) -> {

		LoginService ls = new LoginService();
		String body = ctx.body();

		Gson gson = new Gson();

		LoginDTO LDTO = gson.fromJson(body, LoginDTO.class);

		//control flow for successful login

		//invoke the login() method of LoginService using the username and password in the newly created LoginDTO

		if(LDTO == null) {
			ctx.status(401); //unauthorized status code
			ctx.result("Login Field Empty");
			return;
		}

		User user =  ls.userLogin(LDTO.getUsername(), LDTO.getPassword(), LDTO.getUser_role_id());

		if(user != null) {
			//generate a JSON Web Token to uniquely identify the user
			@SuppressWarnings("unused")
			String jwt = JwtUtil.generate(LDTO.getUsername(), LDTO.getPassword());

			//create a user session
			sessionUser = ctx.req.getSession();
			sessionUser.setAttribute("user", user);

			//successful status code
			ctx.status(200);

			//String JSONUser = gson.toJson(user);
			//ctx.result(JSONUser);
			
			System.out.println(sessionUser.getAttribute("user"));

		} else {
			ctx.status(401); //unauthorized status code
			ctx.result("Login Failed");
		}



	};
	public Handler logoutHandler = (ctx) -> {
		if(ctx.req.getSession(false) != null) {
			sessionUser.invalidate();
			ctx.result("Logged Out");
		} else {
			ctx.result("You are already Logged Out");
		}

	};

} 