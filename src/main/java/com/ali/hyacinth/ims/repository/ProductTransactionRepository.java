package com.ali.hyacinth.ims.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.Product;
import com.ali.hyacinth.ims.model.ProductTransaction;
import com.ali.hyacinth.ims.model.Transaction;

@Repository
public interface ProductTransactionRepository extends PagingAndSortingRepository<ProductTransaction, Long> {
	List<ProductTransaction> findAllByTransaction(Transaction transaction);
	List<ProductTransaction> findAllByProduct(Product product);
}
