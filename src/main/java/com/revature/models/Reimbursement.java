package com.revature.models;

import java.sql.Timestamp;
import java.util.Arrays;

public class Reimbursement {
	private int id;
	private double amount;
	private Timestamp submitted_at;
	private Timestamp resolved_at; 
	private String description;
	private byte[] receipt; // image represented as byte array (bytea in db)
	private User author; // user foreign key
	private User resolver; // user foreign key
	private int status_id; // status foreign key
	private int type_id; // type foreign key
	
	
	public Reimbursement() {
		
	}

	public Reimbursement(int id, double amount, Timestamp submitted_at, Timestamp resolved_at, String description,
			byte[] receipt, User author, User resolver, int status_id, int type_id) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted_at = submitted_at;
		this.resolved_at = resolved_at;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status_id = status_id;
		this.type_id = type_id;
	}

	// possibly constructor used to create new reimb. from site
	public Reimbursement(double amount, String description, User author, int type_id) {
		super();
		this.amount = amount;
		this.description = description;
		this.author = author;
		this.type_id = type_id;
		
		// set the status to pending since it was just created
		this.status_id = 1;
	}

	public Reimbursement(int id, double amount, Timestamp submitted_at, Timestamp resolved_at, String description,
			byte[] receipt, int status_id, int type_id) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted_at = submitted_at;
		this.resolved_at = resolved_at;
		this.description = description;
		this.receipt = receipt;
		this.status_id = status_id;
		this.type_id = type_id;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Timestamp getSubmitted_at() {
		return submitted_at;
	}


	public void setSubmitted_at(Timestamp submitted_at) {
		this.submitted_at = submitted_at;
	}


	public Timestamp getResolved_at() {
		return resolved_at;
	}


	public void setResolved_at(Timestamp resolved_at) {
		this.resolved_at = resolved_at;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public byte[] getReceipt() {
		return receipt;
	}


	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}


	public User getAuthor() {
		return author;
	}


	public void setAuthor(User author) {
		this.author = author;
	}


	public User getResolver() {
		return resolver;
	}


	public void setResolver(User resolver) {
		this.resolver = resolver;
	}


	public int getStatus_id() {
		return status_id;
	}


	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}


	public int getType_id() {
		return type_id;
	}


	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted_at=" + submitted_at + ", resolved_at="
				+ resolved_at + ", description=" + description + ", receipt=" + Arrays.toString(receipt) + ", author="
				+ author + ", resolver=" + resolver + ", status_id=" + status_id + ", type_id=" + type_id + "]";
	}
	
	
	
	
}
