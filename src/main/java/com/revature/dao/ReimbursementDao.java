package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDao implements ReimbursementDaoInterface {

	@Override
	public List<Reimbursement> getAllReimbursements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addReimbursement(Reimbursement reimbursement, int user_id) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			// get current datetime
			Date date = new Date();
			Timestamp currentTimestamp = new Timestamp(date.getTime());
			
			String sql = "INSERT INTO ers_reimbursement "
					+ "(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareCall(sql);
			
			ps.setDouble(1, reimbursement.getAmount());
			ps.setTimestamp(2, currentTimestamp);
			ps.setString(3, reimbursement.getDescription());
			ps.setInt(4, user_id);
			ps.setInt(5, reimbursement.getStatus_id());
			ps.setInt(6, reimbursement.getType_id());
			
			ps.executeUpdate();
			
			System.out.println("Successfully added new reimbursement");
			
			
			
		} catch (SQLException e) {
			System.out.println("Failed to create new reimbursement");
			e.printStackTrace();
		}
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
	public void approveReimbursement(int reimbursement_id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void denyReimbursement(int reimbursement_id) {
		// TODO Auto-generated method stub
	}

}
