package com.ali.hyacinth.ims.service;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;

public interface ImsService {
	
	void login(String username, String password) throws InvalidInputException;
	void logout(String userName);

}
