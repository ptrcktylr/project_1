package com.revature.controllers;

import java.util.List;

import com.google.gson.Gson;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.EmployeeService;

import io.javalin.http.Handler;

public class EmployeeController {

	public Handler addReimbursementHandler = (ctx) -> {

		if(ctx.req.getSession(false) != null) {

			EmployeeService es = new EmployeeService();

			String body = ctx.body();

			Gson gson = new Gson();

			Reimbursement reimbursement = gson.fromJson(body, Reimbursement.class);

			User user = (User) LoginController.sessionUser.getAttribute("user");

			es.addReim(reimbursement, user.getId());
		} else {
			ctx.status(403);
		}

	};

	public Handler viewEmployeeReimHandler = (ctx) -> {

		if(ctx.req.getSession(false) != null) {

			EmployeeService es = new EmployeeService();
			User user = (User) LoginController.sessionUser.getAttribute("user");

			List<Reimbursement> allUsers = es.getPastReim(user.getId());

			Gson gson = new Gson();

			String JSONUser = gson.toJson(allUsers);

			ctx.result(JSONUser);
			ctx.status(200);

		} else {
			ctx.status(403);
		}

	};
	
	public Handler viewPendingEmployeeReimHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {

			EmployeeService es = new EmployeeService();
			User user = (User) LoginController.sessionUser.getAttribute("user");

			List<Reimbursement> allUsers = es.getPendingReims(user.getId());

			Gson gson = new Gson();

			String JSONUser = gson.toJson(allUsers);

			ctx.result(JSONUser);
			ctx.status(200);

		} else {
			ctx.status(403);
		}
	};

}