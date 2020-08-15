package com.ali.hyacinth.ims.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.shared.dto.CustomerDTO;
import com.ali.hyacinth.ims.shared.dto.TransactionDTO;

@Service
public interface CustomerService {
	
	List<TransactionDTO> getCustomerTransactions(String userName);
	
	void createCustomer(CustomerDTO customerDTO, String employeeId) throws InvalidInputException;
	
	void updateCustomer(CustomerDTO customerDTO, String employeeId) throws InvalidInputException;
	
	void deleteCustomer(String userName, String employeeId);
	
	List<CustomerDTO> getCustomers(int page, int limit);
	
	double getCustomerDebt(String userName, String employeeId) throws InvalidInputException;
	
	double getCustomerTransactionDebt(String customerId, Date date) throws InvalidInputException;
	
	List<CustomerDTO> getCustomers();
	
	CustomerDTO customerLogin(String userName, String employeeId) throws InvalidInputException;
	
	void customerLogout(String userName);
	
	CustomerDTO getCustomerTransactions(String userName, String employeeId) throws InvalidInputException;

}
