package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.controllers.EmployeeController;
import com.revature.controllers.LoginController;
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

		try(Connection conn = ConnectionUtil.getConnection()){
			System.out.println("We have connected to the Database");
		} catch(SQLException e) {
			System.out.println("FAILED CONNECTION");
			e.printStackTrace();
		}

		//System.out.println(uD.validLoginByRole("test_manager", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", 2));

		Javalin app = Javalin.create(
				config -> {
					config.enableCorsForAllOrigins(); //allows server to process JS requests from anywhere
				}
				).start(8090);
		
		// persist session attributes between requests
		app.before(ctx -> ctx.header("Access-Control-Allow-Credentials", "true"));
		
		//We use javalin to expose API endpoints, which HTTP Requests
		app.post(("/login"), lc.loginHandler);

		app.get(("/logout"), lc.logoutHandler);

		app.post("/createTicket", ec.addReimbursementHandler);

		app.get("/myTickets", ec.viewEmployeeReimHandler);
		
		// get my pending tickets
		app.get("/myPendingTickets", ec.viewPendingEmployeeReimHandler);
		
		// get all pending tickets
		
		// get all tickets
		
	}

}
