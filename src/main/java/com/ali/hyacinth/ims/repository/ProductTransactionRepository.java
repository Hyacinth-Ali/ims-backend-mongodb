package com.ali.hyacinth.ims.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.Product;
import com.ali.hyacinth.ims.model.ProductTransaction;
import com.ali.hyacinth.ims.model.Transaction;

@Repository
public interface ProductTransactionRepository extends MongoRepository<ProductTransaction, String> {
	List<ProductTransaction> findAllByTransaction(Transaction transaction);
	List<ProductTransaction> findAllByProduct(Product product);
}
