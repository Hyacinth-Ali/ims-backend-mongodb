package com.ali.hyacinth.ims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ali.hyacinth.ims.ImsBackendApplication;
import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.model.Address;
import com.ali.hyacinth.ims.model.Customer;
import com.ali.hyacinth.ims.model.Employee;
import com.ali.hyacinth.ims.model.Transaction;
import com.ali.hyacinth.ims.repository.CustomerRepository;
import com.ali.hyacinth.ims.repository.EmployeeRepository;
import com.ali.hyacinth.ims.repository.TransactionRepository;
import com.ali.hyacinth.ims.service.CustomerService;
import com.ali.hyacinth.ims.shared.Utils;
import com.ali.hyacinth.ims.shared.dto.AddressDTO;
import com.ali.hyacinth.ims.shared.dto.CustomerDTO;
import com.ali.hyacinth.ims.shared.dto.EmployeeDTO;
import com.ali.hyacinth.ims.shared.dto.TransactionDTO;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	Utils utils;

	/**
	 * Retrieves all transactions linked to a customer
	 */
	@Override
	public List<TransactionDTO> getCustomerTransactions(String userName) throws InvalidInputException {

		if (userName == null || userName.length() == 0) {
			throw new InvalidInputException("Please select the customer.");
		}

		Customer customer = customerRepository.findByUserName(userName);

		if (customer == null) {
			throw new InvalidInputException("This customer does not exist.");
		}

		List<Transaction> transactions = transactionRepository.findAllByBuyer(customer);

		ArrayList<TransactionDTO> returnValuie = new ArrayList<TransactionDTO>();

		ModelMapper modelMapper = new ModelMapper();

		for (Transaction transaction : transactions) {
			returnValuie.add(modelMapper.map(transaction, TransactionDTO.class));
		}
		return returnValuie;
	}

	/**
	 * Create an object of a customer.
	 * 
	 * @throws InvalidInputException
	 */
	@Override
	public void createCustomer(CustomerDTO customerDto, String employeeId) throws InvalidInputException {

		if (!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("A manager is required.");
		}

		String error = "";

		if (customerRepository.findByUserName(customerDto.getUserName()) != null) {
			error = "The user name already exist.";
		} else if (customerDto.getUserName() == null || customerDto.getUserName().length() == 0) {
			error = "The user name of a customer cannot be empty";
		} else if (customerDto.getFirstName() == null || customerDto.getFirstName().length() == 0) {
			error = "The first name of a customer cannot be empty.";
		} else if (customerDto.getLastName() == null || customerDto.getLastName().length() == 0) {
			error = "The last name of a customer cannot be empty.";
		} else if (customerDto.getPhoneNumber() == null || customerDto.getPhoneNumber().length() == 0) {
			error = "The phone number cannot be empty.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		ModelMapper modelMapper = new ModelMapper();

		Customer customer = modelMapper.map(customerDto, Customer.class);

		String publicCustomerId = utils.generateEmployeeId(30);
		customer.setCustomerId(publicCustomerId);

		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	/**
	 * Checks if a manager is logged in
	 * @param employeeId of the manager
	 * @return loggedIn status of the manager
	 */
	private boolean isManagerLoggedIn(String employeeId) {
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		boolean loggedIn = false;
		if (employee == null) {
			throw new InvalidInputException("An employee with such details does not exist.");
		} else if (!employee.isManager()) {
			throw new InvalidInputException("A manager is required.");
		}
		for (Employee e : ImsBackendApplication.getCurrentEmployees()) {
			if (e.getUserName().equals(employee.getUserName())) {
				loggedIn = true;
			}
		}
		return loggedIn;
	}

	/**
	 * Retrieve debt of a customer.
	 * 
	 * @param userName of the customer
	 * @return the amount of the debt
	 * @throws InvalidInputException
	 */
	@Override
	public double getCustomerDebt(String userName, String employeeId) throws InvalidInputException {

		if (!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("A manager is required.");
		}

		// Get the customer from the database
		Customer customer = customerRepository.findByUserName(userName);

		double debt = 0;

		if (customer != null) {
			List<Transaction> transactions = transactionRepository.findAllByBuyer(customer);
			for (Transaction transaction : transactions) {
				debt += transaction.getAmountUnpaid();
			}
		} else {
			throw new InvalidInputException("The customer does not exist.");
		}

		return debt;
	}

	/**
	 * Retrieves specific debt of a customer for a transaction, not used for now
	 * TODO to be fully implemented and used
	 * 
	 * @param customerId of the customer
	 * @return the amount of the debt
	 * @throws InvalidInputException
	 */
	@Override
	public double getCustomerTransactionDebt(String customerId, Date date) throws InvalidInputException {
		Customer customer = customerRepository.findByUserName(customerId);
		double returnValue = 0;
		if (customer != null) {
			List<Transaction> transactions = transactionRepository.findAllByBuyer(customer);
			for (Transaction transaction : transactions) {
				if (transaction.getTransactionDate().equals(date))
					;
				returnValue = transaction.getAmountUnpaid();
			}
		}
		return returnValue;
	}

	@Override
	public void updateCustomer(CustomerDTO customerDTO, String employeeId) throws InvalidInputException {
		String error = "";

		if (!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("A manager is required.");
		}

		Customer customer = customerRepository.findByUserName(customerDTO.getUserName());
		if (customer == null) {
			throw new InvalidInputException("The customer does not exist");
		}

		if (customerDTO.getFirstName() == null || customerDTO.getFirstName().length() == 0) {
			error = "The first name of a customer cannot be empty";
		} else if (customerDTO.getLastName() == null || customerDTO.getLastName().length() == 0) {
			error = "The last of a customer cannot be empty";
		} else if (customerDTO.getUserName() == null || customerDTO.getUserName().length() == 0) {
			error = "The user name of a customer cannot be empty";
		} else if (customerDTO.getPhoneNumber() == null || customerDTO.getPhoneNumber().length() == 0) {
			error = "The phone number cannot be empty.";
		}

		// ModelMapper modelMapper = new ModelMapper();
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setPhoneNumber(customerDTO.getPhoneNumber());

		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	/**
	 * Delete an instance of a customer from the database
	 * 
	 * @param userName of the customer
	 */
	@Transactional
	@Override
	public void deleteCustomer(String userName, String employeeId) {

		if (!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("A manager is required.");
		}

		Customer customer = customerRepository.findByUserName(userName);

		if (customer == null) {
			throw new InvalidInputException("Customer doesn't exist");
		}

		try {
			customerRepository.delete(customer);
		} catch (Exception e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	/**
	 * TODO: To fully implemented and then used
	 */
	@Override
	public List<CustomerDTO> getCustomers(int page, int limit) {

		if (limit <= 0) {
			throw new InvalidInputException("The size must be greater than zero");
		}

		if (page > 0) {
			page = page - 1;
		}

		List<CustomerDTO> returnValue = new ArrayList<>();

		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<Customer> customerPage = customerRepository.findAll(pageableRequest);
		List<Customer> customers = customerPage.getContent();

		for (Customer customer : customers) {
			CustomerDTO customerDTO = new CustomerDTO();
			BeanUtils.copyProperties(customer, customerDTO);
			returnValue.add(customerDTO);
		}
		return returnValue;
	}

	/**
	 * Retrieves the list of customers from the database
	 *
	 */
	@Override
	public List<CustomerDTO> getCustomers() {
		List<CustomerDTO> returnValue = new ArrayList<CustomerDTO>();

		Iterable<Customer> customers = customerRepository.findAll();

		ModelMapper modelMapper = new ModelMapper();

		for (Customer customer : customers) {
			returnValue.add(modelMapper.map(customer, CustomerDTO.class));
		}
		return returnValue;
	}

	/**
	 * Logs in a customer with a user name
	 * 
	 * @param userName of the customer
	 * 
	 * @return the customer
	 */
	@Override
	public CustomerDTO customerLogin(String userName, String employeeId) throws InvalidInputException {

		if(!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("A manager is required to register a customer.");
		}
		
		Customer customer = customerRepository.findByUserName(userName);
		if (customer == null) {
			throw new InvalidInputException("The user name is incorrect, try again!");
		}
		
		for (Customer c : ImsBackendApplication.getCurrentCustomers()) {
			if (c.getUserName().equals(customer.getUserName())) {
				throw new InvalidInputException("The customer is already logged in");
			}
		}

		ImsBackendApplication.addCurrentCustomer(customer);
		
		CustomerDTO returnValue = new CustomerDTO();
		ModelMapper modelMapper = new ModelMapper();
		
		Iterable<Transaction> transactions = transactionRepository.findAllByBuyer(customer);
		List<TransactionDTO> transactionDTOs = new ArrayList<TransactionDTO>();
		
		for (Transaction transaction : transactions) {
			transactionDTOs.add(modelMapper.map(transaction, TransactionDTO.class));
		}
		returnValue = modelMapper.map(customer, CustomerDTO.class);
		returnValue.setTransactions(transactionDTOs);
		return returnValue;

	}

	@Override
	public void customerLogout(String userName) {
		
		Customer customer = customerRepository.findByUserName(userName);
		if (customer == null) {
			throw new InvalidInputException("The user name is incorrect, try again!");
		}
		Customer currentCustomer = null;
		for (Customer c : ImsBackendApplication.getCurrentCustomers()) {
			if (c.getUserName().equals(userName)) {
				currentCustomer = c;
			}
		}
		if (currentCustomer != null) {
			ImsBackendApplication.getCurrentCustomers().remove(currentCustomer);
		} else {
			throw new InvalidInputException("This customer was not logged in!");
		}
		
	}

	@Override
	public CustomerDTO getCustomerTransactions(String userName, String employeeId)
			throws InvalidInputException {
		
		String error = "";
		if (!isManagerLoggedIn(employeeId)) {
			error = "A manager must log in before this request.";
		}
		
		Customer customer = customerRepository.findByUserName(userName);
		
		if (customer == null) {
			error = "The customer does not exist or incorrect details.";
		} else if (userName == null || userName.length() == 0) {
			error = "The user name of a customer cannot be empty";
		} 
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		CustomerDTO returnValue = new CustomerDTO();
		ModelMapper modelMapper = new ModelMapper();
		
		Iterable<Transaction> transactions = transactionRepository.findAllByBuyer(customer);
		List<TransactionDTO> transactionDTOs = new ArrayList<TransactionDTO>();
		
		for (Transaction transaction : transactions) {
			transactionDTOs.add(modelMapper.map(transaction, TransactionDTO.class));
		}
		returnValue = modelMapper.map(customer, CustomerDTO.class);
		returnValue.setTransactions(transactionDTOs);
		return returnValue;
	}

}
