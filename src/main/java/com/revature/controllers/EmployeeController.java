package com.revature.controllers;

import java.util.List;

import com.google.gson.Gson;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.EmployeeService;

import io.javalin.http.Handler;

public class EmployeeController {

	public Handler addReimbursementHandler = (ctx) -> {

		User user = (User) LoginController.sessionUser.getAttribute("user");
		
		// check user logged in and user is an employee
		if (ctx.req.getSession(false) != null && user.getRole_id() == 1) {

			EmployeeService es = new EmployeeService();

			String body = ctx.body();

			Gson gson = new Gson();

			Reimbursement reimbursement = gson.fromJson(body, Reimbursement.class);
				
			if(reimbursement.getAmount() == 0 || reimbursement.getType_id() == 0) {
				ctx.result("Amount and Type fields must be filled");
				ctx.status(400);
				return;
			}else {
				reimbursement.setStatus_id(1);
				es.addReim(reimbursement, user.getId());
			}

		} else {
			ctx.result("Employee Permission only");
			ctx.status(403);
		}
	};

	public Handler viewEmployeeReimHandler = (ctx) -> {
		
		User user = (User) LoginController.sessionUser.getAttribute("user");

		if(ctx.req.getSession(false) != null && user.getRole_id() == 1) {

			EmployeeService es = new EmployeeService();

			List<Reimbursement> allUsers = es.getPastReim(user.getId());

			Gson gson = new Gson();

			String JSONUser = gson.toJson(allUsers);

			ctx.result(JSONUser);
			ctx.status(200);

		} else {
			ctx.result("Employee Permission only");
			ctx.status(403);
		}

	};
	
	public Handler viewPendingEmployeeReimHandler = (ctx) -> {
		
		User user = (User) LoginController.sessionUser.getAttribute("user");
		
		if(ctx.req.getSession(false) != null && user.getRole_id() == 1) {

			EmployeeService es = new EmployeeService();

			List<Reimbursement> allUsers = es.getPendingReims(user.getId());

			Gson gson = new Gson();

			String JSONUser = gson.toJson(allUsers);

			ctx.result(JSONUser);
			ctx.status(200);

		} else {
			ctx.result("Employee Permission only");
			ctx.status(403);
		}
	};

}