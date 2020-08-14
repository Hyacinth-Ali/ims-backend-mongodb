package com.ali.hyacinth.ims.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
public class Employee {
	
	private String password;
	private boolean manager;
	private String firstName;
	private String lastName;
	//TODO: not working here, done in the mongo shell
	// db.collection.createIndex("email": 1}, {unique: 1})
	@Indexed(unique = true)
	private String email;
	@Indexed(unique = true)
	private String userName;
	private String encryptedPassword;
	@Id
	private String employeeId;
	// this takes a number of parameters.
	//@DBRef(db = <name_of_external_databse>)
	//@DBRef(lazy = false>) is default. This means that the referenced is loaded 
	//when referencing object is retrieved from database
	@DBRef()
	private Set<Transaction> sales;
	@DBRef
	private Set<Address> addresss;

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
