package com.revature.models;

import java.util.Arrays;

public class Reimbursement {
	private int id;
	private double amount;
	private String submitted_at; // timestamp
	private String resolved_at; // timestamp
	private String description;
	private byte[] receipt; // image represented as byte array (bytea in db)
	private int author_id; // user foreign key
	private int resolver_id; // user foreign key
	private int status_id; // status foreign key
	private int type_id; // type foreign key
	
	
	public Reimbursement() {
		
	}
	
	public Reimbursement(int id, double amount, String submitted_at, String resolved_at, String description,
			byte[] receipt, int author_id, int resolver_id, int status_id, int type_id) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted_at = submitted_at;
		this.resolved_at = resolved_at;
		this.description = description;
		this.receipt = receipt;
		this.author_id = author_id;
		this.resolver_id = resolver_id;
		this.status_id = status_id;
		this.type_id = type_id;
	}

	// possibly constructor used to create new reimb. from site
	public Reimbursement(double amount, String description, int author_id, int type_id) {
		super();
		this.amount = amount;
		this.description = description;
		this.author_id = author_id;
		this.type_id = type_id;
		
		// set the status to pending since it was just created
		this.status_id = 1;
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


	public String getSubmitted_at() {
		return submitted_at;
	}


	public void setSubmitted_at(String submitted_at) {
		this.submitted_at = submitted_at;
	}


	public String getResolved_at() {
		return resolved_at;
	}


	public void setResolved_at(String resolved_at) {
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


	public int getAuthor_id() {
		return author_id;
	}


	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}


	public int getResolver_id() {
		return resolver_id;
	}


	public void setResolver_id(int resolver_id) {
		this.resolver_id = resolver_id;
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
	
	
	
}
