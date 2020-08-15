package com.ali.hyacinth.ims.service;

import org.springframework.stereotype.Service;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.shared.dto.ProductTransactionDTO;
import com.ali.hyacinth.ims.shared.dto.TransactionDTO;
import com.ali.hyacinth.ims.shared.dto.TransactionDetail;

@Service
public interface TransactionService {
	
	
	TransactionDTO createTransaction(String employeeId, String customerUserName) throws InvalidInputException;
	
	ProductTransactionDTO addTransactionProduct(String employeeId, String productName, int quantity, String transactionId) throws InvalidInputException;
	
	
	//Final step to purchase products, generate receipt.
	TransactionDetail getTransactionDetail(String employeeId, String transactionId, String customerUserName) throws InvalidInputException;
	
	//Receipt generateReceipt(String customerID) throws InvalidInputException;
	
	//List of products for a given transaction 
	//List<ProductTransactionDTO> getTOProductTransaction(String transactionId);
	
	void deleteTransaction(String transactionId, String employeeId) throws InvalidInputException;
	
	void clearTransactionProducts(String employeeId, String transactionId) throws InvalidInputException;
	
	void updateQuantityTransaction(String employeeID, String productName, int newQuantity, String transactionId) throws InvalidInputException;
	
	void updateAmountPaidTransaction(double newAmount, String transactionId, String employeeId) throws InvalidInputException;
	
	void removeProduct(String employeeId, String productName, String transactionId) throws InvalidInputException;
	
	void finalizeTransaction(String transactionID, String employeeId);

}









