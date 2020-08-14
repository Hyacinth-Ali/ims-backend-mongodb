package com.ali.hyacinth.ims.model;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {

	private String phoneNumber;
	private String firstName;
	private String lastName;
	private long id;
	private String userName;
	private Set<Transaction> purchases;
	private String customerId;


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<Transaction> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Transaction> purchases) {
		this.purchases = purchases;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
