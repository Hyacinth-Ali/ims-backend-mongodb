package com.ali.hyacinth.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.service.ImsService;

@Service
public class ImsServiceImpl implements ImsService {
	
	@Autowired
	EmployeeServiceImpl employeeService;

	@Override
	public void login(String username, String password) throws InvalidInputException {
		
		if (username.contains("@")) {
			employeeService.getEmployeeByEmail(username, password);
		} else {
			employeeService.getEmployeeByUserName(username, password);
		}
		
		
		
	}

	@Override
	public void logout(String userName) {
		// TODO Auto-generated method stub
		
	}

}
