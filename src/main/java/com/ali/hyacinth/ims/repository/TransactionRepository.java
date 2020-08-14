package com.ali.hyacinth.ims.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ali.hyacinth.ims.model.Customer;
import com.ali.hyacinth.ims.model.Transaction;


public interface TransactionRepository extends MongoRepository<Transaction, Long> {
	
	List<Transaction> findAllByBuyer(Customer buyer);
	//List<Transaction> findAllByCustomer(Customer customer);
	Transaction findByTransactionId(String transactionId);
	//List<Transaction> findAllByProducts(Product product);

}
