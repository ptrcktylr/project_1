package com.revature.controllers;

import java.util.List;

import com.google.gson.Gson;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ManagerService;

import io.javalin.http.Handler;

public class ManagerController {

	public Handler approveReimbursementHandler = (ctx) -> {
		
		User user = (User) LoginController.sessionUser.getAttribute("user");

		if(ctx.req.getSession(false) != null && user.getRole_id() == 2) {

			ManagerService ms = new ManagerService();
			int reim_id = Integer.parseInt(ctx.pathParam("r_id"));
			ms.approveReim(reim_id,user.getId());

			ctx.result("Updated Request");
			ctx.status(200);

		} else {
			ctx.result("Manager Permission only");
			ctx.status(403);
		}

	};

	public Handler denyReimbursementHandler = (ctx) -> {

		User user = (User) LoginController.sessionUser.getAttribute("user");
		
		if(ctx.req.getSession(false) != null && user.getRole_id() == 2) {

			ManagerService ms = new ManagerService();
			int reim_id = Integer.parseInt(ctx.pathParam("r_id"));
			ms.denyReim(reim_id,user.getId());

			ctx.result("Updated Request");
			ctx.status(200);

		} else {
			ctx.result("Manager Permission only");
			ctx.status(403);
		}

	};

	public Handler viewAllReimHandler = (ctx) -> {

		User user = (User) LoginController.sessionUser.getAttribute("user");
		
		if(ctx.req.getSession(false) != null && user.getRole_id() == 2) {

			ManagerService ms = new ManagerService();

			List<Reimbursement> allUsers = ms.viewAllReim();

			Gson gson = new Gson();

			String JSONUser = gson.toJson(allUsers);

			ctx.result(JSONUser);
			ctx.status(200);

		} else {
			ctx.result("Manager Permission only");
			ctx.status(403);
		}
	};


	public Handler viewPendingReimHandler = (ctx) -> {
		
		User user = (User) LoginController.sessionUser.getAttribute("user");

		if(ctx.req.getSession(false) != null && user.getRole_id() == 2) {

			ManagerService ms = new ManagerService();

			List<Reimbursement> pendingUsers = ms.getPendingReim();

			Gson gson = new Gson();

			String JSONUser = gson.toJson(pendingUsers);

			ctx.result(JSONUser);
			ctx.status(200);


		} else {
			ctx.result("Manager Permission only");
			ctx.status(403);
		}

	};
}