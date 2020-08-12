package com.ali.hyacinth.ims.service;

import java.util.List;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.shared.dto.EmployeeDTO;

public interface EmployeeService {

	void createEmployee(EmployeeDTO employeeDTO) throws InvalidInputException;
	
	void updateEmployee(String id, EmployeeDTO employeeDTO) throws InvalidInputException;
	
	void deleteEmployee(String employeeId);

	EmployeeDTO getEmployeeByUserName(String userName, String password);
	
	void logout(String userName);
	
	EmployeeDTO getEmployeeByEmail(String email, String password);
	
	List<EmployeeDTO> getEmployees();
	
	List<EmployeeDTO> getEmployees(int page, int limit);
	
	List<EmployeeDTO> getManagers();
	

}
