package com.ali.hyacinth.ims.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ali.hyacinth.ims.ImsBackendApplication;
import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.model.Customer;
import com.ali.hyacinth.ims.model.Employee;
import com.ali.hyacinth.ims.model.Product;
import com.ali.hyacinth.ims.model.ProductTransaction;
import com.ali.hyacinth.ims.model.Transaction;
import com.ali.hyacinth.ims.repository.CustomerRepository;
import com.ali.hyacinth.ims.repository.EmployeeRepository;
import com.ali.hyacinth.ims.repository.ProductRepository;
import com.ali.hyacinth.ims.repository.ProductTransactionRepository;
import com.ali.hyacinth.ims.repository.TransactionRepository;
import com.ali.hyacinth.ims.service.TransactionService;
import com.ali.hyacinth.ims.shared.Utils;
import com.ali.hyacinth.ims.shared.dto.ProductTransactionDTO;
import com.ali.hyacinth.ims.shared.dto.TransactionDTO;
import com.ali.hyacinth.ims.shared.dto.TransactionDetail;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	ProductTransactionRepository productTransactionRepository;

	

	/**
	 * Creates instance of a {@link Transaction}
	 * @param customerUserName of the buyer
	 * @param date of the transaction
	 * @throws InvalidInputException
	 */
	@Override
	public TransactionDTO createTransaction(String employeeId, String customerUserName) throws InvalidInputException {
		String error = "";
		//get the current date
		String date = ImsBackendApplication.getCurrentDate();

		if(!isManagerLoggedIn(employeeId)) {
			error = "Manager must be logged in.";
		} else if (customerUserName == null || customerUserName.length() == 0) {
			error = "Please, enter the customer user name.";
		} 
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		Customer c = customerRepository.findByUserName(customerUserName);
		Employee e = employeeRepository.findByEmployeeId(employeeId);
		
		
		if (c == null) {
			error = "The customer does not exist, register first.";
		} else if (e == null){
			error = "The customer user name is invalid.";
		} else if (!e.isManager()) {
			error = "You need a manager to create a transaction.";
		} 
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		Transaction transaction = new Transaction();
		transaction.setTransactionDate(date);
		transaction.setBuyer(c);
		transaction.setSeller(e);
		transaction.setEditable(true);
		String publicTransactionId = utils.generateEmployeeId(30);
		transaction.setTransactionId(publicTransactionId);
		
		try {
			transactionRepository.save(transaction);
		} catch (Exception er) {
			throw new InvalidInputException(er.getMessage());
		}
		
		ModelMapper modelMapper = new ModelMapper();
		TransactionDTO returnValue = modelMapper.map(transaction, TransactionDTO.class);
		
		return returnValue;

	}
	
	private boolean isCurrentEmployee(String employeeId) {
		String name = null;
		for (Employee e : ImsBackendApplication.getCurrentEmployees()) {
			if (e.getEmployeeId().equals(employeeId)) {
				name = employeeId;
			}
		}
		
		return name != null;
	}

	@Override
	public ProductTransactionDTO addTransactionProduct(String employeeId, String productName, int quantity, String transactionId) throws InvalidInputException {
		
		String error = "";
		
		if(!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("Manager must be logged in.");
		}
		
		Product product = productRepository.findByName(productName);
		Transaction currentTransaction = transactionRepository.findByTransactionId(transactionId);
		
		if (currentTransaction == null) {
			error = "The transaction does not exist.";
		} else if (!currentTransaction.isEditable()) {
			error = "This transaction cannot be modified";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
	
		boolean productExist = false;
		
		List<ProductTransaction> productTs = productTransactionRepository.findAllByTransaction(currentTransaction);
		if (product != null) {
			for (ProductTransaction pt : productTs) {
				if (pt.getProduct().getProductId().equals(product.getProductId())) {
					productExist = true;
				}
			}
		} else {
			error = "The product does not exist.";
		}
		
		
		if (productExist) {
			error = "The product is already added, you can edit it.";
		} else if (quantity <= 0) {
			error = "Quantity of items must be greater than zero.";
		} 
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		if(quantity > product.getQuantity()) {
			error = "Sorry! we do not have enough product in store.";
		} 
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		ProductTransaction productTransaction = new ProductTransaction();
		productTransaction.setProduct(product);
		//productTransaction.setProductName(product.getName());
		productTransaction.setQuantity(quantity);
		productTransaction.setPrice(quantity * product.getItemPrice());
		String publicpTransactionId = utils.generateEmployeeId(30);
		productTransaction.setpTransactionId(publicpTransactionId);
		productTransaction.setTransaction(currentTransaction);
		
		product.setQuantity(product.getQuantity() - quantity);
		Set<ProductTransaction> pts = product.getProductTransactions();
		if (pts == null) {
			pts = new HashSet<ProductTransaction>();
		}
		pts.add(productTransaction);
		product.setProductTransactions(pts);
		
		List<ProductTransaction> productTransactions = productTransactionRepository.findAllByTransaction(currentTransaction);
		productTransactions.add(productTransaction);
		currentTransaction.setProductTransactions((productTransactions));
		
		try {
			productTransactionRepository.save(productTransaction);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, check your details and try again");
		}
		
		setTransactionTotalAmount(currentTransaction.getTransactionId());
		
		
		try {
			productRepository.save(product);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, check your details and try again");
		}
		
		
		try {
			transactionRepository.save(currentTransaction);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, check your details and try again");
		}
		 
		ModelMapper modelMapper = new ModelMapper();
		ProductTransactionDTO returnValue = modelMapper.map(productTransaction, ProductTransactionDTO.class);
		
		return returnValue;

	}

	
	private void setTransactionTotalAmount(String transactionId) throws InvalidInputException {
		
		Transaction currentTransaction = transactionRepository.findByTransactionId(transactionId);
		
		float totalAmount = 0.0f;
		if (currentTransaction != null) {
			for (ProductTransaction productTransaction : productTransactionRepository.findAllByTransaction(currentTransaction)) {
				double amount = productTransaction.getPrice();
				totalAmount += amount;
			}
		} else {
			throw new InvalidInputException("There transaction does not exist.");
		}
		
		currentTransaction.setTotalAmount(totalAmount);
		
//		try {
//			transactionRepository.save(currentTransaction);
//		} catch (Exception e) {
//			throw new InvalidInputException("Error!, check your details and try again");
//		}

	}

	/**
	 * Final step to purchase products, generate receipt.
	 * @param transaction of the purchase
	 * @throws InvalidInputException
	 */
	@Override
	public TransactionDetail getTransactionDetail(String employeeId, String transactionId, String customerUserName) throws InvalidInputException {
		//TODO remove userName
		if(!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("Manager must be logged in.");
		}
		
		String error = "";
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
		Customer customer = customerRepository.findByUserName(customerUserName);
		if (transaction == null) {
			error = "This transaction does not exist!";
		} else if (customer == null) {
			error = "The customer user name is incorrect";
		} 
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		TransactionDetail transactionDetails = new TransactionDetail();
		transactionDetails.setFirstName(customer.getFirstName());
		transactionDetails.setLastName(customer.getLastName());
		transactionDetails.setPhoneNumber(customer.getPhoneNumber());
		transactionDetails.setCustomerName(customerUserName);
		transactionDetails.setTransactionId(transaction.getTransactionId());
		transactionDetails.setTotalAmount(transaction.getTotalAmount());
		transactionDetails.setAmountPaid(transaction.getAmountPaid());
		transactionDetails.setAmountUnpaid(transaction.getAmountUnpaid());
		String date = transaction.getTransactionDate();
		transactionDetails.setDate(date);
		
		try {
			for (ProductTransaction pTransaction : productTransactionRepository.findAllByTransaction(transaction)) {
				
				ProductTransactionDTO pTransactionDTO = new ProductTransactionDTO();
				
				ModelMapper modelMapper = new ModelMapper();
				
				pTransactionDTO = modelMapper.map(pTransaction, ProductTransactionDTO.class);
				transactionDetails.getpTransactions().add(pTransactionDTO);
			}
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		return transactionDetails;
	}

	/**
	 * Deletes a transaction
	 * @param transactionId of the transaction
	 * @throws InvalidInputException
	 */
	@Transactional
	@Override
	public void deleteTransaction(String transactionId, String employeeId) throws InvalidInputException {
		
		if(!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("Manager must be logged in.");
		}
		
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		
		if (transaction == null) {
			throw new InvalidInputException("The transaction doesn't exist");
		} else if (employee == null) {
			throw new InvalidInputException("The employee user name is incorrect");
		} 

		String id = null;
		for (Employee e : ImsBackendApplication.getCurrentEmployees()) {
			if (e.getEmployeeId().equals(employeeId)) {
				id = employeeId;
			}
		}
		
		if (id == null || id.length() == 0) {
			throw new InvalidInputException("Employee must be logged in.");
		}
		
		try {
			for (ProductTransaction productTransaction : productTransactionRepository.findAllByTransaction(transaction)) {
				Product p = productTransaction.getProduct();
				p.setQuantity(p.getQuantity() + productTransaction.getQuantity());
				productRepository.save(p);
			}
			
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		transactionRepository.delete(transaction);

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

	//Not used for now TODO
	@Override
	public void clearTransactionProducts(String employeeId, String transactionId) throws InvalidInputException {
		
//		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
//		if (transaction == null) {
//			throw new InvalidInputException("The transaction does not exist");
//		}
//		
//		try {
//			for (ProductTransaction productTransaction : productTransactionRepository.findAllByTransaction(transaction)) {
//				Product p = productRepository.findByProductTransaction(productTransaction);
//				p.setQuantity(p.getQuantity() + productTransaction.getQuantity());
//				productRepository.save(p);
//			}
//			List<ProductTransaction> clearTransactions = productTransactionRepository.findAllByTransaction(transaction);
//			productTransactionRepository.deleteAll(clearTransactions);
//			setTransactionTotalAmount(transactionId);
//		} catch (RuntimeException e) {
//			throw new InvalidInputException(e.getMessage());
//		}

	}

	@Override
	public void updateQuantityTransaction(String employeeID, String productName, int newQuantity, String transactionId) throws InvalidInputException {
		
		String error = "";
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
		Product product = productRepository.findByName(productName);
		
		if (transaction == null) {
			error = "The transaction does not exist.";
		} else if (!transaction.isEditable()) {
			error = "This transaction cannot be modified";
		} else if (product == null) {
			error = "This product doesn't exist for this transaction";
		} else if (!isManagerLoggedIn(employeeID)) {
			error = "A manager is required to make the changes.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		ProductTransaction pTransaction = null;
		List<ProductTransaction> pTransactions = productTransactionRepository.findAllByProduct(product);
		for (ProductTransaction pt : pTransactions) {
			if (transaction.getProductTransactions().contains(pt)) {
				 pTransaction = pt;
				 break;
			}
		}
		
		if (pTransaction == null) {
			throw new InvalidInputException("This item transaction doesn't exist");
		}
		int differenceQuantity = newQuantity - pTransaction.getQuantity();
		
		if (product.getQuantity() - differenceQuantity < 0) {
			throw new InvalidInputException("Sorry!, we do not have enough products for this quantity.");
		}
		
		pTransaction.setQuantity(newQuantity);	
		pTransaction.setPrice(newQuantity * product.getItemPrice());
		product.setQuantity(product.getQuantity() - differenceQuantity);
		setTransactionTotalAmount(transactionId);
		try {
			transactionRepository.save(transaction);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, The transaction was not saved.");
		}
		
		try {
			productRepository.save(product);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, The product was not saved.");
		}

	}
	
	@Override
	public void finalizeTransaction(String transactionID, String employeeId) {
		String error = "";
		Transaction transaction = transactionRepository.findByTransactionId(transactionID);
		
		if (transaction == null) {
			error = "The transaction does not exist.";
		} else if (!isManagerLoggedIn(employeeId)) {
			error = "A manager is required to make the changes.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		transaction.setEditable(false);
		
		try {
			transactionRepository.save(transaction);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, The transaction was not saved.");
		}
	}

	@Override
	public void updateAmountPaidTransaction(double newAmount, String transactionId, String employeeId) throws InvalidInputException {
		
		String error = "";
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
		if (!isManagerLoggedIn(employeeId)) {
			error = "A manager is required to make the request";
		} else if (transaction == null) {
			error = "The transaction does not exist.";
		} 
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		transaction.setAmountPaid(transaction.getAmountPaid() + newAmount);
		
		try {
			transactionRepository.save(transaction);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, check your details and try again");
		}

	}

	@Transactional
	@Override
	public void removeProduct(String employeeId, String productName, String transactionId) throws InvalidInputException {
		
		if(!isManagerLoggedIn(employeeId)) {
			throw new InvalidInputException("A manager is required to make the request.");
		}
		String error = "";
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
		Product product = productRepository.findByName(productName);
		
		if (transaction == null) {
			error = "The transaction does not exist.";
		} else if (!transaction.isEditable()) {
			error = "This transaction cannot be modified";
		} else if (product == null) {
			error = "This product doesn't exist";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		
		ProductTransaction deletetPTranaction = null;
		for (ProductTransaction pTransaction : transaction.getProductTransactions()) {
			if (pTransaction.getProduct().getName().equals(product.getName())) {
				deletetPTranaction = pTransaction;
				break;
			}
		}

		if (deletetPTranaction == null) {
			throw new InvalidInputException("The product is not in this tranaction");
		}
		int quantity = deletetPTranaction.getQuantity();
		product.setQuantity(product.getQuantity() + quantity);
		transaction.getProductTransactions().remove(deletetPTranaction);
		productTransactionRepository.delete(deletetPTranaction);
		setTransactionTotalAmount(transactionId);
		
		for (ProductTransaction pTransaction : transaction.getProductTransactions()) {
			if (pTransaction.getProduct().getName().equals(product.getName())) {
				deletetPTranaction = pTransaction;
				break;
			}
		}
		
		try {
			transactionRepository.save(transaction);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, The transaction was not saved");
		}
		try {
			productRepository.save(product);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, The product was not saved");
		}
	}

	}
