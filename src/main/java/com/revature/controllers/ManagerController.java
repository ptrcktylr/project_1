package com.revature.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ManagerService;

import io.javalin.http.Handler;

public class ManagerController {

	public Handler approveReimbursementHandler = (ctx) -> {
		
		Logger log = LogManager.getLogger(ManagerController.class);
		
		if(ctx.req.getSession(false) != null) {
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 2) {
				
				ManagerService ms = new ManagerService();
				int reim_id = Integer.parseInt(ctx.pathParam("r_id"));
				ms.approveReim(reim_id,user.getId());
		
				ctx.result("Updated Request");
				ctx.status(200);
				
				log.info("Approved reimbursement ID: " + reim_id + " by manager: " + user.getUsername());
				
			} else {
				ctx.status(403);
				ctx.result("Manager Permission only");
			}


			
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");

		}

	};

	public Handler denyReimbursementHandler = (ctx) -> {
		
		Logger log = LogManager.getLogger(ManagerController.class);

		if(ctx.req.getSession(false) != null) {
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 2) {
				
				ManagerService ms = new ManagerService();
				int reim_id = Integer.parseInt(ctx.pathParam("r_id"));
				ms.denyReim(reim_id,user.getId());
		
				ctx.result("Updated Request");
				ctx.status(200);
				
				log.info("Denied reimbursement ID: " + reim_id + " by manager: " + user.getUsername());
				
			} else {
				ctx.status(403);
				ctx.result("Manager Permission only");
				
			}


			
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");

		}

	};

	public Handler viewAllReimHandler = (ctx) -> {

		if(ctx.req.getSession(false) != null) {
			
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 2) {
				
				ManagerService ms = new ManagerService();
				
				List<Reimbursement> allUsers = ms.viewAllReim();

				Gson gson = new Gson();
				
				String JSONUser = gson.toJson(allUsers);
				
				ctx.result(JSONUser);
				ctx.status(200);
				
			} else {
				ctx.status(403);
				ctx.result("Manager Permission only");
			}


			
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");

		}
	};

	public Handler viewPendingReimHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 2) {
				
				ManagerService ms = new ManagerService();
				
				List<Reimbursement> pendingUsers = ms.getPendingReim();

				Gson gson = new Gson();
				
				String JSONUser = gson.toJson(pendingUsers);
				
				ctx.result(JSONUser);
				ctx.status(200);
				
			} else {
				ctx.status(403);
				ctx.result("Manager Permission only");
			}

			
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");

		}

	};
	
	public Handler viewReimbHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			User user = (User) LoginController.sessionUser.getAttribute("user");
			
			if(user.getRole_id() == 2) {
				
				ManagerService ms = new ManagerService();
				int reim_id = Integer.parseInt(ctx.pathParam("r_id"));
				Reimbursement reimb = ms.getReim(reim_id);

				Gson gson = new Gson();
				
				String JSONReimb = gson.toJson(reimb);
				
				ctx.result(JSONReimb);
				ctx.status(200);
				
			} else {
				ctx.status(403);
				ctx.result("Manager Permission only");
			}

			
		} else {
			ctx.status(403);
			ctx.result("Not Logged in");

		}

	};
}