package com.revature;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.EmployeeService;
import com.revature.services.LoginService;
import com.revature.services.ManagerService;

public class Tests {
	
	// login works with correct credentials
	@Test
	void testSuccessfulLogin() {
		LoginService ls = new LoginService();
		User user = ls.userLogin("patrick", "password");
		assertEquals("patrick", user.getUsername());
	}
	
	// login fails with incorrect credentials
	@Test
	void testFailedLogin() {
		LoginService ls = new LoginService();
		User user = ls.userLogin("notarealuser", "notarealpassword");
		assertNull(user);
	}
	
	// adding reimb successful if input is valid
	@Test
	void testAddReimb() {
		LoginService ls = new LoginService();
		EmployeeService es = new EmployeeService();
		
		// create user for author
		User user = ls.userLogin("patrick", "password");
		
		// create reimb
		Reimbursement testReimb = new Reimbursement(
				10.99, "This is a test reimbursement", user, 1);
		
		// add reimb to database
		boolean added = es.addReim(testReimb, user.getId());
		
		// return true if successfully added
		assertTrue(added);
	}
	
	// adding reimb fails if input not valid
	@Test
	void testAddReimbFail() {
		LoginService ls = new LoginService();
		EmployeeService es = new EmployeeService();
		
		// create user for author
		User user = ls.userLogin("patrick", "password");
		
		// create reimb
		Reimbursement testReimb = new Reimbursement();
		
		// add reimb to database
		boolean added = es.addReim(testReimb, user.getId());
		
		// return true if successfully added
		assertFalse(added);
	}
	
	// searching by reimb id returns that reimb with that id if it exists
	@Test
	void getValidReimb() {
		ManagerService ms = new ManagerService();
		
		Reimbursement reimb = ms.getReim(3);
		assertEquals(3, reimb.getId());
	}
	
	// searching by reimb id returns null if that id doesn't exist
	@Test
	void getMissingReimb() {
		ManagerService ms = new ManagerService();
		
		Reimbursement reimb = ms.getReim(999999999);
		assertNull(reimb);
	}
	
	// all tickets returns all tickets
	@Test
	void getAllReimbs() {
	}
	
	// all pending tickets returns only pending tickets 
	// deny reimb
	// approve reimb

}
