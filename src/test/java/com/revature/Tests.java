package com.revature;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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
		
		Reimbursement reimb = ms.getReim(2);
		assertEquals(2, reimb.getId());
	}
	
	// searching by reimb id returns null if that id doesn't exist
	@Test
	void getMissingReimb() {
		ManagerService ms = new ManagerService();
		
		Reimbursement reimb = ms.getReim(0);
		assertNull(reimb);
	}
	
	// all tickets returns all tickets
	@Test
	void getAllReimbs() {
		ManagerService ms = new ManagerService();
		List<Reimbursement> reimbs = ms.viewAllReim();
		assertTrue(reimbs.size() != 0);
	}
	
	// all pending tickets returns only pending tickets 
	@Test
	void getAllPendingReimbs() {
		ManagerService ms = new ManagerService();
		
		// get all pending
		List<Reimbursement> reimbs = ms.getPendingReim();
		
		// if any are not pending, fail
		boolean allReimbsPending = true;
		for (Reimbursement reimb : reimbs) {
			if (reimb.getStatus_id() != 1) {
				allReimbsPending = false;
			}
		}
		assertTrue(allReimbsPending);
	}
	
	// deny reimb
	@Test
	void denyReimb() {
		LoginService ls = new LoginService();
		EmployeeService es = new EmployeeService();
		ManagerService ms = new ManagerService();
		
		// create user for author
		User author = ls.userLogin("patrick", "password");
		// create user for resolver
		User resolver = ls.userLogin("manager_funsocks", "password");
		
		// create reimb
		Reimbursement testReimb = new Reimbursement(
				10.99, "This is a test reimbursement", author, 1);
		
		// add reimb to database
		es.addReim(testReimb, author.getId());
		
		// get all pending
		List<Reimbursement> reimbs = ms.getPendingReim();
		
		// get last added, set it to deny
		int reimb_id = reimbs.get(reimbs.size()-1).getId();
		ms.denyReim(reimb_id, resolver.getId());
		
		// return true if reimb is denied
		assertEquals(3, ms.getReim(reimb_id).getStatus_id());
	}
	
	// approve reimb
	@Test
	void approveReimb() {
		LoginService ls = new LoginService();
		EmployeeService es = new EmployeeService();
		ManagerService ms = new ManagerService();
		
		// create user for author
		User author = ls.userLogin("patrick", "password");
		// create user for resolver
		User resolver = ls.userLogin("manager_funsocks", "password");
		
		// create reimb
		Reimbursement testReimb = new Reimbursement(
				10.99, "This is a test reimbursement", author, 1);
		
		// add reimb to database
		es.addReim(testReimb, author.getId());
		
		// get all pending
		List<Reimbursement> reimbs = ms.getPendingReim();
		
		// get last added, set it to approve
		int reimb_id = reimbs.get(reimbs.size()-1).getId();
		ms.approveReim(reimb_id, resolver.getId());
		
		// return true if reimb is approved
		assertEquals(2, ms.getReim(reimb_id).getStatus_id());
	}

}
