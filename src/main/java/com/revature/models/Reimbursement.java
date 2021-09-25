package com.revature.models;

import java.util.Arrays;

public class Reimbursement {
	private int id;
	private double amount;
	private String submitted_at; // timestamp
	private String resolved_at; // timestamp
	private String description;
	private byte[] receipt; // image represented as byte array (bytea in db)
	private User author; // User object, foreign key in db
	private User resolver; // User object, foreign key in db
	private int status_id; // foreign key
	private int type_id; // foreign key
	
	public Reimbursement(int id, double amount, String submitted_at, String resolved_at, String description,
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

	public Reimbursement(double amount, String submitted_at, String resolved_at, String description, byte[] receipt,
			User author, User resolver, int status_id, int type_id) {
		super();
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

	public Reimbursement(double amount, String description, byte[] receipt, User author, int status_id, int type_id) {
		super();
		this.amount = amount;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.status_id = status_id;
		this.type_id = type_id;
	}

	public Reimbursement(double amount, String description, byte[] receipt, int status_id, int type_id) {
		super();
		this.amount = amount;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result + ((resolved_at == null) ? 0 : resolved_at.hashCode());
		result = prime * result + ((resolver == null) ? 0 : resolver.hashCode());
		result = prime * result + status_id;
		result = prime * result + ((submitted_at == null) ? 0 : submitted_at.hashCode());
		result = prime * result + type_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (!Arrays.equals(receipt, other.receipt))
			return false;
		if (resolved_at == null) {
			if (other.resolved_at != null)
				return false;
		} else if (!resolved_at.equals(other.resolved_at))
			return false;
		if (resolver == null) {
			if (other.resolver != null)
				return false;
		} else if (!resolver.equals(other.resolver))
			return false;
		if (status_id != other.status_id)
			return false;
		if (submitted_at == null) {
			if (other.submitted_at != null)
				return false;
		} else if (!submitted_at.equals(other.submitted_at))
			return false;
		if (type_id != other.type_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted_at=" + submitted_at + ", resolved_at="
				+ resolved_at + ", description=" + description + ", receipt=" + Arrays.toString(receipt) + ", author="
				+ author + ", resolver=" + resolver + ", status_id=" + status_id + ", type_id=" + type_id + "]";
	}
	
	
	
}
