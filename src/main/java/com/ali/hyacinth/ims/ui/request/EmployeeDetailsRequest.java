package com.ali.hyacinth.ims.ui.request;

import java.util.List;

public class EmployeeDetailsRequest {
	
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private List<AddressRequestModel> addresses;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the addresses
	 */
	public List<AddressRequestModel> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<AddressRequestModel> addresses) {
		this.addresses = addresses;
	}
	
	
	

}
