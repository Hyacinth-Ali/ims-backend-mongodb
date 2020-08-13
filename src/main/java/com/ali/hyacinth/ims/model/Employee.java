package com.ali.hyacinth.ims.model;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
public class Employee implements Serializable {
	@Id
	private long id;
	private String password;
	private boolean manager;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String encryptedPassword;
	private String employeeId;
	private Set<Transaction> sales;
	private Set<Address> addresss;

	private static final long serialVersionUID = 8282812751828419590L;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Set<Transaction> getSales() {
		return sales;
	}

	public void setSales(Set<Transaction> sales) {
		this.sales = sales;
	}

	public Set<Address> getAddresss() {
		return addresss;
	}

	public void setAddresss(Set<Address> addresss) {
		this.addresss = addresss;
	}

}
