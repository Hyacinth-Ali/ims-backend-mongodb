package com.ali.hyacinth.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
	
	Product findByName(String name);
	//Product findByProductTransaction(ProductTransaction pTransaction);
	//long 

}
