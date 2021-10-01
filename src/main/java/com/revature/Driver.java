package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.controllers.EmployeeController;
import com.revature.controllers.LoginController;
import com.revature.controllers.ManagerController;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.utils.ConnectionUtil;

import io.javalin.Javalin;

@SuppressWarnings("unused") // will remove after testing is done
public class Driver {
	
	public static void main(String[] args) {
		UserDao uD = new UserDao();
		EmployeeController ec = new EmployeeController();
		LoginController lc = new LoginController();
		ManagerController mc = new ManagerController();

		try(Connection conn = ConnectionUtil.getConnection()){
			System.out.println("We have connected to the Database");
		} catch(SQLException e) {
			System.out.println("FAILED CONNECTION");
			e.printStackTrace();
		}

		Javalin app = Javalin.create(
				config -> {
					config.enableCorsForAllOrigins(); //allows server to process JS requests from anywhere
				}
				).start(8090);
		
		// persist session attributes between requests
		app.before(ctx -> ctx.header("Access-Control-Allow-Credentials", "true"));
		
		app.post(("/login"), lc.loginHandler);
		app.post(("/logout"), lc.logoutHandler);

		app.post("/createTicket", ec.addReimbursementHandler);
		app.get("/myTickets", ec.viewEmployeeReimHandler);
		app.get("/myPending", ec.viewPendingEmployeeReimHandler);
		
		
		//Manager Controller
		app.get("/allTickets", mc.viewAllReimHandler);
		app.get("/pendingTickets", mc.viewPendingReimHandler);
		app.patch("/approveTicket/:r_id", mc.approveReimbursementHandler);
		app.patch("/denyTicket/:r_id", mc.denyReimbursementHandler);
		
	}

}
