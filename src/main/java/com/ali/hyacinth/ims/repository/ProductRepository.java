package com.ali.hyacinth.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ali.hyacinth.ims.model.Product;


public interface ProductRepository extends MongoRepository<Product, Long> {
	
	Product findByName(String name);
	//Product findByProductTransaction(ProductTransaction pTransaction);
	//long 

}
