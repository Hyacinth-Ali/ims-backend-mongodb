package com.ali.hyacinth.ims.ui.response;

import java.util.List;

import com.ali.hyacinth.ims.shared.dto.TransactionDTO;

public class CustomerRest {
	
	private String firstName;
	private String lastName;
	private String userName;
	List<TransactionDTO> transactions;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<TransactionDTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}
}
