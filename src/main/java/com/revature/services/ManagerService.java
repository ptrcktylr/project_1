package com.revature.services;

import java.util.List;

import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;

public class ManagerService {

	public List<Reimbursement> viewAllReim() {

		ReimbursementDao rD = new ReimbursementDao();
		return rD.getAllReimbursements();
	}

	public List<Reimbursement> getPendingReim() {
		ReimbursementDao rD = new ReimbursementDao();
		return rD.getAllReimbursementsByStatus(1);
	}

	public void approveReim(int reimbursement_id, int resolver) {

		ReimbursementDao rD = new ReimbursementDao();
		rD.approveReimbursement(reimbursement_id, resolver);

	}

	public void denyReim(int reimbursement_id, int resolver) {
		ReimbursementDao rD = new ReimbursementDao();
		rD.denyReimbursement(reimbursement_id, resolver);
	}

}