package com.revature.dao;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDaoInterface {

	// get all reimbursements
	public List<Reimbursement> getAllReimbursements();
	
	// add new reimbursement, return its id when created
	public int addReimbursement(Reimbursement reimbursement);
	
	// remove a reimbursement (with its id)
	public void removeReimbursement(int reimbursement_id);
	
	// get all reimbursements from specific user (with user id)
	public List<Reimbursement> getAllReimbursementsByUser(int user_id);
	
	// get all reimbursements by status id (open, closed)
	public List<Reimbursement> getAllReimbursementsByStatus(int status_id);
	
	// approve reimbursement (with its id), return it as a Reimbursement
	public Reimbursement approveReimbursement(int reimbursement_id);
	
	// deny reimbursement (with its id), return it as a Reimbursement
	public Reimbursement denyReimbursement(int reimbursement_id);
	
}
