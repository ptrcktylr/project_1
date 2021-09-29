package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDao implements ReimbursementDaoInterface {

	@Override
	public List<Reimbursement> getAllReimbursements() {
		
		UserDao UDao = new UserDao();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT * FROM ers_reimbursement";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
			
			while (rs.next()) {
				Reimbursement reimbursement = new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getBytes("reimb_receipt"),
						rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id")
						);
				reimbursement.setAuthor(UDao.getUserById(rs.getInt("reimb_author")));
				
				if (rs.getInt("reimb_resolver") != 0) {
					reimbursement.setResolver(UDao.getUserById(rs.getInt("reimb_resolver")));
				}
				
				reimbursementList.add(reimbursement);
			}
			
			return reimbursementList;
			
		} catch (SQLException e) {
			System.out.println("Failed to get all reimbursements");
			e.printStackTrace();
		}
		
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
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, reimbursement_id);
			ps.executeUpdate();
			
			System.out.println("Reimbursement with ID " + reimbursement_id + " deleted successfuly!");
			
		} catch (SQLException e) {
			
			System.out.println("Failed to delete reimbursement!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Reimbursement> getAllReimbursementsByUser(int user_id) {
		UserDao uDao = new UserDao();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ResultSet rs = null;
			
			String sql = "select * from ers_reimbursement where reimb_author = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, user_id);
			
			rs = ps.executeQuery();
			
			List<Reimbursement> reimburse_list = new ArrayList<>();
			
			Reimbursement reimburst = null;
			
			while(rs.next()) {
				
				reimburst = new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getBytes("reimb_receipt"),
						null,
						null,
						rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id"));
				
				// System.out.println(rs.getInt("reimb_resolver"));
				
				if(rs.getInt("reimb_resolver") != 0){
					reimburst.setResolver(uDao.getUserById(rs.getInt("reimb_resolver")));
				}
				reimburst.setAuthor(uDao.getUserById(rs.getInt("reimb_author")));

				reimburse_list.add(reimburst);
			}
			
			return reimburse_list;
			
			
		} catch(SQLException e) {
			System.out.println("Something went wrong with your checkusername database");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Reimbursement> getAllReimbursementsByStatus(int status_id) {
		UserDao uDao = new UserDao();

		try(Connection conn = ConnectionUtil.getConnection()){

			ResultSet rs = null;

			String sql = "select * from ers_reimbursement where reimb_status_id = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, status_id);

			rs = ps.executeQuery();

			List<Reimbursement> reimburse_list = new ArrayList<>();

			Reimbursement reimburst = null;

			while(rs.next()) {

				reimburst = new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getBytes("reimb_receipt"),
						null,
						null,
						rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id"));

				System.out.println(rs.getInt("reimb_resolver"));

				if(rs.getInt("reimb_resolver") != 0){
					reimburst.setResolver(uDao.getUserById(rs.getInt("reimb_resolver")));
				}
				reimburst.setAuthor(uDao.getUserById(rs.getInt("reimb_author")));



				reimburse_list.add(reimburst);
			}

			return reimburse_list;


		} catch(SQLException e) {
			System.out.println("Failed to get all reimbursements by status id!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Reimbursement> getAllPendingReimbursementsByUser(int user_id) {
		
		UserDao uDao = new UserDao();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ResultSet rs = null;
			
			String sql = "select * from ers_reimbursement where reimb_author = ? and reimb_status_id = 1";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, user_id);
			
			rs = ps.executeQuery();
			
			List<Reimbursement> reimburse_list = new ArrayList<>();
			
			Reimbursement reimburst = null;
			
			while(rs.next()) {
				
				reimburst = new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getBytes("reimb_receipt"),
						null,
						null,
						rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id"));
				
				// System.out.println(rs.getInt("reimb_resolver"));
				
				if(rs.getInt("reimb_resolver") != 0){
					reimburst.setResolver(uDao.getUserById(rs.getInt("reimb_resolver")));
				}
				reimburst.setAuthor(uDao.getUserById(rs.getInt("reimb_author")));

				reimburse_list.add(reimburst);
			}
			
			return reimburse_list;
			
			
		} catch(SQLException e) {
			System.out.println("Something went wrong with your checkusername database");
			e.printStackTrace();
		}
		return null;
		
	}
	
	@Override
	public void approveReimbursement(int reimbursement_id) {
		// TODO: possibly return reimbursement object
		try(Connection conn = ConnectionUtil.getConnection()){

			String sql = "UPDATE ers_reimbursement SET reimb_status_id = 2 WHERE reimb_id = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, reimbursement_id);

			ps.executeUpdate();


		} catch(SQLException e) {
			System.out.println("Something went wrong with your checkusername database");
			e.printStackTrace();
		}
	}

	@Override
	public void denyReimbursement(int reimbursement_id) {
		// TODO: possibly return reimbursement object
				try(Connection conn = ConnectionUtil.getConnection()){

					String sql = "UPDATE ers_reimbursement SET reimb_status_id = 3 WHERE reimb_id = ?";

					PreparedStatement ps = conn.prepareStatement(sql);

					ps.setInt(1, reimbursement_id);

					ps.executeUpdate();


				} catch(SQLException e) {
					System.out.println("Something went wrong with your checkusername database");
					e.printStackTrace();
				}
	}

	
	

}
