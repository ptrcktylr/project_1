package com.revature.dao;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDaoInterface {

	// get all reimbursements
	public List<Reimbursement> getAllReimbursements();
	
	// add new reimbursement, return its id when created
	public void addReimbursement(Reimbursement reimbursement, int user_id);
	
	// remove a reimbursement (with its id)
	public void removeReimbursement(int reimbursement_id);
	
	// get all reimbursements from specific user (with user id)
	public List<Reimbursement> getAllReimbursementsByUser(int user_id);
	
	// get all reimbursements by status id (open, closed)
	public List<Reimbursement> getAllReimbursementsByStatus(int status_id);
	
	// get all PENDING reimbursements from specific user (with user id)
	public List<Reimbursement> getAllPendingReimbursementsByUser(int user_id);
	
	// approve reimbursement (with its id), return it as a Reimbursement
	public void approveReimbursement(int reimbursement_id);
	
	// deny reimbursement (with its id), return it as a Reimbursement
	public void denyReimbursement(int reimbursement_id);
	
}
