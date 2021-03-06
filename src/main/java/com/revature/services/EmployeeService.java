package com.revature.services;

import java.util.List;

import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;

public class EmployeeService {

	ReimbursementDao rD = new ReimbursementDao();

	public List<Reimbursement> getPastReim(int id){
		return rD.getAllReimbursementsByUser(id);
	}
	
	public List<Reimbursement> getPendingReims(int id){
		return rD.getAllPendingReimbursementsByUser(id);
	}

	public boolean addReim(Reimbursement reimbursement, int user_id) {
		return rD.addReimbursement(reimbursement, user_id);
	}
	
	public Reimbursement getReim(int reimbursement_id, int user_id) {
		ReimbursementDao rD = new ReimbursementDao();
		return rD.getUserReimbursementById(reimbursement_id, user_id);
	}

}