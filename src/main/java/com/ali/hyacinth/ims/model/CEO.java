package com.ali.hyacinth.ims.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ceo")
public class CEO  {
	
	private String password;
	private String firstName;
	private String lastName;
	@Indexed(unique = true)
	private String email;
	private String encryptedPassword;
	@Id
	private String ceoId;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getCeoId() {
		return ceoId;
	}

	public void setCeoId(String ceoId) {
		this.ceoId = ceoId;
	}

}
