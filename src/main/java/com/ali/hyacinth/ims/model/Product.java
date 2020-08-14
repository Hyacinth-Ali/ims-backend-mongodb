package com.ali.hyacinth.ims.model;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
public class Product {
	private double itemPrice;
	private int quantity;
	private long id;
	private String name;
	private String productId;
	@DBRef
	private Set<ProductTransaction> productTransactions;

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Set<ProductTransaction> getProductTransactions() {
		return productTransactions;
	}

	public void setProductTransactions(Set<ProductTransaction> productTransactions) {
		this.productTransactions = productTransactions;
	}

}
