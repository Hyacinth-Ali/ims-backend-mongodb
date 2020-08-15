package com.ali.hyacinth.ims.service;

import org.springframework.stereotype.Service;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;

@Service
public interface ImsService {
	
	void login(String username, String password) throws InvalidInputException;
	void logout(String userName);

}
