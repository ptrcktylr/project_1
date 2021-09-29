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

	public void addReim(Reimbursement reimbursement, int user_id) {
		rD.addReimbursement(reimbursement, user_id);
	}

}