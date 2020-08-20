package com.ali.hyacinth.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ali.hyacinth.ims.ImsBackendApplication;
import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.model.Employee;
import com.ali.hyacinth.ims.model.Transaction;
import com.ali.hyacinth.ims.repository.EmployeeRepository;
import com.ali.hyacinth.ims.repository.TransactionRepository;
import com.ali.hyacinth.ims.service.EmployeeService;
import com.ali.hyacinth.ims.shared.Utils;
import com.ali.hyacinth.ims.shared.dto.EmployeeDTO;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	Utils utils;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	TransactionRepository transactionRepository;

	/**
	 * Create an instance of an employee.
	 * 
	 * @param EmployeeDTO the details of the new employee
	 * @param person      to be associated with the manager.
	 * @throws InvalidInputException
	 */
	@Override
	public void createEmployee(EmployeeDTO employeeDTO) {

		String error = "";

		if (employeeRepository.findByEmail(employeeDTO.getEmail()) != null) {
			error = "The email already exist.";
		} else if (employeeRepository.findByUserName(employeeDTO.getUserName()) != null) {
			error = "The user name already exist.";
		} else if (employeeDTO.getFirstName() == null || employeeDTO.getFirstName().length() == 0) {
			error = "The first name cannot be empty.";
		} else if (employeeDTO.getLastName() == null || employeeDTO.getLastName().length() == 0) {
			error = "The last name cannot be empty.";
		} else if (employeeDTO.getPassword() == null || employeeDTO.getPassword() == "") {
			error = "The password cannot be empty.";
		} else if (employeeDTO.getUserName() == null || employeeDTO.getUserName().length() == 0) {
			error = "The user name cannot be empty.";
		} else if (employeeDTO.getEmail() == null || employeeDTO.getEmail().length() == 0) {
			error = "The email cannot be empty.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		ModelMapper modelMapper = new ModelMapper();

		Employee employee = modelMapper.map(employeeDTO, Employee.class);

		employee.setManager(true);
		String publicUserId = utils.generateEmployeeId(30);
		employee.setEmployeeId(publicUserId);
		employee.setEncryptedPassword("testpassword");

		try {
			employeeRepository.save(employee);
		} catch (Exception e) {
			throw new InvalidInputException("Error! Pleae check your details");
		}
	}

	/**
	 * Retrieve a given employee with his public id
	 * 
	 * @param userName user name of the employee
	 * @param password
	 * @return the employee
	 */
	@Override
	public EmployeeDTO getEmployeeByUserName(String userName, String password) {

		String error = "";
		if (userName == null || userName.length() == 0) {
			error = "Please enter your user name or email address.";
		} else if (password == null || password.length() == 0) {
			error = "Please enter your password.";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		EmployeeDTO returnValue = new EmployeeDTO();
		Employee employee = employeeRepository.findByUserName(userName);
		if (employee == null) {
			throw new InvalidInputException("The user name is incorrect, try again!");
		}

		if (!employee.getPassword().equals(password)) {
			throw new InvalidInputException("Incorrect password, try again!");
		}

		BeanUtils.copyProperties(employee, returnValue);

		ImsBackendApplication.addCurrentEmployee(employee);

		return returnValue;
	}

	/**
	 * Removes current employee
	 */
	public void logout(String userName) {

		ImsBackendApplication.removeCurrentEmployee(userName);
	}

	/**
	 * Retrieve a given employee with his email
	 * 
	 * @param password
	 * @param userName user name of the employee
	 * 
	 * @return the employee
	 */
	@Override
	public EmployeeDTO getEmployeeByEmail(String email, String password) {

		String error = "";

		if (email == null || email.length() == 0) {
			error = "Please enter your user name or email address";
		} else if (password == null || password.length() == 0) {
			error = "Please enter your password.";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		EmployeeDTO returnValue = new EmployeeDTO();
		Employee employee = employeeRepository.findByEmail(email);
		if (employee == null) {
			throw new InvalidInputException("The email is incorrect, try again!");
		}
		if (!employee.getPassword().equals(password)) {
			throw new InvalidInputException("Incorrect password, try again!");
		}

		ImsBackendApplication.addCurrentEmployee(employee);

		BeanUtils.copyProperties(employee, returnValue);

		return returnValue;
	}

	@Transactional
	@Override
	public void deleteEmployee(String employeeId) {

		Employee employee = employeeRepository.findByEmployeeId(employeeId);

		if (employee == null) {
			throw new InvalidInputException("The employee doesn't exist");
		}
		
		//TODO: delete the referenced employee
		List<Transaction> transactions = transactionRepository.findAllBySeller(employee);
//		transactions.forEach(transaction -> {
//			Query query = new Query();
//			query.addCriteria(Criteria.where("seller").is("xxx.xxx@xxx.com"));
//			Update update = new Update();
//			update.unset("activationToken");
//
//			// run update operation
//			transactionRepository.
//			mongoTemplate.updateMulti(transactionRepository.findAllBySeller(employee), update, Transaction.class);
//		});

		try {
			employeeRepository.delete(employee);
		} catch (Exception e) {
			throw new InvalidInputException("Error! Something went wrong. Check your details and try again");
		}

	}

	/**
	 * Query method for retrieving managers
	 */
	@Override
	public void updateEmployee(String id, EmployeeDTO employeeDTO) throws InvalidInputException {
		String error = "";

		if (employeeDTO.getFirstName() == null || employeeDTO.getFirstName().length() == 0) {
			error = "The first name cannot be empty.";
		} else if (employeeDTO.getLastName() == null || employeeDTO.getLastName().length() == 0) {
			error = "The last name cannot be empty.";
		} else if (employeeDTO.getPassword() == null || employeeDTO.getPassword() == "") {
			error = "The password cannot be empty.";
		} else if (employeeDTO.getUserName() == null || employeeDTO.getUserName().length() == 0) {
			error = "The user name cannot be empty.";
		} else if (employeeDTO.getEmail() == null || employeeDTO.getEmail().length() == 0) {
			error = "The email cannot be empty.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		Employee employee = employeeRepository.findByUserName(employeeDTO.getUserName());
		Employee employeeById = employeeRepository.findByEmployeeId(id);

		if (employee == null) {
			throw new InvalidInputException("The user name is incorrect.");
		}

		if (employeeById == null) {
			throw new InvalidInputException("Access Denied!");
		} else if (!employeeById.getUserName().equals(employee.getUserName())) {
			throw new InvalidInputException("Access Denied!");
		}

		if (!employee.getPassword().equals(employeeDTO.getPassword())) {
			error = "The password is incorrect";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setPassword(employeeDTO.getPassword());
		employee.setEmail(employeeDTO.getEmail());
		// cannot update user name for now
		// employee.setUserName(employeeDTO.getUserName());

		try {
			employeeRepository.save(employee);
		} catch (Exception e) {
			throw new InvalidInputException("Error! Seems the email already exist");
		}

	}

	/**
	 * Retrieves all the employees from the database.
	 */
	@Override
	public List<EmployeeDTO> getEmployees() {
		ArrayList<EmployeeDTO> returnValue = new ArrayList<EmployeeDTO>();
		ModelMapper modelMapper = new ModelMapper();

		for (Employee employee : employeeRepository.findAll()) {
			returnValue.add(modelMapper.map(employee, EmployeeDTO.class));

		}
		return returnValue;
	}

	@Override
	public List<EmployeeDTO> getEmployees(int page, int limit) {

		if (page > 0) {
			page = page - 1;
		}
		if (limit <= 0) {
			throw new InvalidInputException("The size cannot be zero or less");
		}

		List<EmployeeDTO> returnValue = new ArrayList<>();

		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<Employee> employeePage = employeeRepository.findAll(pageableRequest);
		List<Employee> employees = employeePage.getContent();

		for (Employee employee : employees) {
			EmployeeDTO employeeDTO = new EmployeeDTO();
			BeanUtils.copyProperties(employee, employeeDTO);
			returnValue.add(employeeDTO);
		}
		return returnValue;
	}

	@Override
	public List<EmployeeDTO> getManagers() {
		ArrayList<EmployeeDTO> returnValue = new ArrayList<EmployeeDTO>();
		ModelMapper modelMapper = new ModelMapper();

		for (Employee employee : employeeRepository.findAll()) {
			if (employee.isManager()) {
				returnValue.add(modelMapper.map(employee, EmployeeDTO.class));
			}
		}
		return returnValue;
	}

}
