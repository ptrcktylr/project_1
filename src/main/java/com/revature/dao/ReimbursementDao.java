package com.revature.dao;

import java.util.List;

import com.revature.models.Reimbursement;

public class ReimbursementDao implements ReimbursementDaoInterface {

	@Override
	public List<Reimbursement> getAllReimbursements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addReimbursement(Reimbursement reimbursement) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeReimbursement(int reimbursement_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reimbursement> getAllReimbursementsByUser(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reimbursement> getAllReimbursementsByStatus(int status_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement approveReimbursement(int reimbursement_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement denyReimbursement(int reimbursement_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
