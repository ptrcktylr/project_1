package com.revature.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.EmployeeService;

import io.javalin.http.Handler;

public class EmployeeController {

	public Handler addReimbursementHandler = (ctx) -> {
		
		Logger log = LogManager.getLogger(EmployeeController.class);

		if(ctx.req.getSession(false) != null) {
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 1) {
				
				EmployeeService es = new EmployeeService();
				
				String body = ctx.body();
				
				Gson gson = new Gson();
				
				Reimbursement reimbursement = gson.fromJson(body, Reimbursement.class);
				
				//Add ifStatement and return if the fields are not filled out
				if(reimbursement.getAmount() == 0 || reimbursement.getType_id() == 0) {
					ctx.result("Amount and Type fields must be filled");
					ctx.status(400);
					return;
				}else {
					reimbursement.setStatus_id(1);
					
					es.addReim(reimbursement, user.getId());
					log.info("Added new reimbursement by user: " + user.getUsername());
 
					
				}
				
			} else {
				ctx.status(403);
				ctx.result("Employee Permission only");
			}
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");
		}
	};

	public Handler viewEmployeeReimHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 1) {
				EmployeeService es = new EmployeeService();

				List<Reimbursement> allUsers = es.getPastReim(user.getId());

				Gson gson = new Gson();
				
				String JSONUser = gson.toJson(allUsers);
				
				ctx.result(JSONUser);
				ctx.status(200);
			} else {
				ctx.status(403);
				ctx.result("Employee Permission only");
			}


			
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");

		}

	};
	
	public Handler viewPendingEmployeeReimHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 1) {
				EmployeeService es = new EmployeeService();

				List<Reimbursement> pendingReim = es.getPendingReims(user.getId());

				Gson gson = new Gson();
				
				String JSONReim = gson.toJson(pendingReim);
				
				ctx.result(JSONReim);
				ctx.status(200);
			} else {
				ctx.status(403);
				ctx.result("Employee Permission only");
			}


			
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");

		}
	};
	
	
	public Handler viewReimbHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 1) {
				
				EmployeeService es = new EmployeeService();
				int reim_id = Integer.parseInt(ctx.pathParam("r_id"));
				Reimbursement reimb = es.getReim(reim_id, user.getId());
				
				if (reimb == null) {
					ctx.status(403);
					ctx.result("This is not your ticket!");
					return;
				}

				Gson gson = new Gson();
				
				String JSONReimb = gson.toJson(reimb);
				
				ctx.result(JSONReimb);
				ctx.status(200);
				
			} else {
				ctx.status(403);
				ctx.result("Employee Permission only");
			}

			
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");

		}

	};
	

}