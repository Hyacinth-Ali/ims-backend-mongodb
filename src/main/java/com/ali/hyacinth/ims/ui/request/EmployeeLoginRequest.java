package com.ali.hyacinth.ims.ui.request;

public class EmployeeLoginRequest {
	
	// this can be a user name or email address
	private String identifier;
	private String password;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
