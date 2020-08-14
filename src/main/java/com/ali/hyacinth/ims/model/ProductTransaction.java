package com.ali.hyacinth.ims.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ProductTransaction")
public class ProductTransaction {
	private double price;
	@DBRef
	private Product product;
	@DBRef
	private Transaction transaction;
	private long id;
	private String pTransactionId;
	private int quantity;
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getpTransactionId() {
		return pTransactionId;
	}
	public void setpTransactionId(String pTransactionId) {
		this.pTransactionId = pTransactionId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
