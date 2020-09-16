package com.ali.hyacinth.ims.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ali.hyacinth.ims.service.TransactionService;
import com.ali.hyacinth.ims.shared.dto.ProductTransactionDTO;
import com.ali.hyacinth.ims.shared.dto.TransactionDTO;
import com.ali.hyacinth.ims.shared.dto.TransactionDetail;
import com.ali.hyacinth.ims.ui.request.TransactionProductDetails;
import com.ali.hyacinth.ims.ui.request.TransactionRequestDetails;

@RestController
@RequestMapping("transactions") // http://localhost:8080/transactions
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	// http://localhost:8080/transactions/{employeeId}
	@PostMapping(path = "/{employeeId}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public TransactionDTO createTransaction(@PathVariable String employeeId,
			@RequestBody TransactionRequestDetails transactionRequestDetails) throws Exception {

		// ModelMapper modelMapper = new ModelMapper();
		// TransactionDTO transactionDTO = modelMapper.map(transactionRequestDetails,
		// TransactionDTO.class);

		String customerUserName = transactionRequestDetails.getCustomerUserName();

		TransactionDTO returnValue = transactionService.createTransaction(employeeId, customerUserName);
		
		return returnValue;
	}

	// http://localhost:8080/transactions/:id
	@PostMapping(path = "/{employeeId}/{transactionId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ProductTransactionDTO addTransactionProduct(@PathVariable String transactionId,
			@PathVariable String employeeId,
			@RequestBody TransactionProductDetails transactionProductDetails) throws Exception {

		String productName = transactionProductDetails.getProductName();
		int quantity = transactionProductDetails.getQuantity();

		ProductTransactionDTO returnValue = 
				transactionService.addTransactionProduct(employeeId, productName, quantity, transactionId);
		return returnValue;
	}

	// http://localhost:8080/transactions/:id/:transactionId
	@GetMapping(path = "/{employeeId}/{transactionId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public TransactionDetail getTransactionDetail(
			@PathVariable String employeeId,
			@PathVariable String transactionId,
			@RequestBody TransactionRequestDetails transactionRequestDetails) {

		String customerUserName = transactionRequestDetails.getCustomerUserName();
		TransactionDetail returnValue = transactionService.getTransactionDetail(employeeId, transactionId, customerUserName);
		return returnValue;
	}

	@PutMapping(path = "/{employeeId}/{transactionId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public void updateQuantity(@PathVariable String employeeId, @PathVariable String transactionId,
			@RequestBody TransactionProductDetails productDetails) {

		String productName = productDetails.getProductName();
		int newQuantity = productDetails.getQuantity();

		transactionService.updateQuantityTransaction(employeeId, productName, newQuantity, transactionId);

	}

	@PutMapping(path = "/amount/{transactionId}/{employeeId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public void updateAmountPaid(@PathVariable String transactionId,
			@PathVariable String employeeId,
			@RequestBody TransactionRequestDetails requestDetails) {

		double newAmount = requestDetails.getAmountPaid();

		transactionService.updateAmountPaidTransaction(newAmount, transactionId, employeeId);

	}

	@DeleteMapping(path = "/{productName}/{transactionId}/{employeeId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public void removeProduct(@PathVariable String employeeId, @PathVariable String productName,
			@PathVariable String transactionId) {

		transactionService.removeProduct(employeeId, productName, transactionId);
	}

	@DeleteMapping(path = "/{transactionId}/{employeeId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public void deleteTransaction(@PathVariable String transactionId, @PathVariable String employeeId) {

		transactionService.deleteTransaction(transactionId, employeeId);
	}
	
	@PutMapping(path = "/finalize/{transactionId}/{employeeId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public void finalizeTransaction(@PathVariable String transactionId,
			@PathVariable String employeeId) {

		transactionService.finalizeTransaction(transactionId, employeeId);

	}

}
