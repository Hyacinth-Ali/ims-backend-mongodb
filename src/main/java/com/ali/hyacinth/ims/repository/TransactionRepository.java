package com.ali.hyacinth.ims.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.Customer;
import com.ali.hyacinth.ims.model.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
	
	List<Transaction> findAllByBuyer(Customer buyer);
	//List<Transaction> findAllByCustomer(Customer customer);
	Transaction findByTransactionId(String transactionId);
	//List<Transaction> findAllByProducts(Product product);

}
