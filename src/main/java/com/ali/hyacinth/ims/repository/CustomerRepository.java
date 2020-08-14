package com.ali.hyacinth.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ali.hyacinth.ims.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Long> {
	
	Customer findByUserName(String userName);

}
