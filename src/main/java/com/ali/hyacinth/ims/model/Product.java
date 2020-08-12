package com.ali.hyacinth.ims.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "products")
public class Product implements Serializable {
	private double itemPrice;

	public void setItemPrice(double value) {
		this.itemPrice = value;
	}

	@Column(nullable = false)
	public double getItemPrice() {
		return this.itemPrice;
	}

	private int quantity;

	public void setQuantity(int value) {
		this.quantity = value;
	}

	@Column(nullable = false)
	public int getQuantity() {
		return this.quantity;
	}

	private long id;

	public void setId(long value) {
		this.id = value;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}

	private String name;

	public void setName(String value) {
		this.name = value;
	}

	@Column(unique = true, nullable = false)
	public String getName() {
		return this.name;
	}

	private String productId;

	public void setProductId(String value) {
		this.productId = value;
	}

	@Column(unique = true, nullable = false)
	public String getProductId() {
		return this.productId;
	}

	private Set<ProductTransaction> productTransactions;

	@OneToMany(mappedBy = "product")
	//@JoinColumn(name = "productTransaction_id")
	public Set<ProductTransaction> getProductTransactions() {
		return this.productTransactions;
	}

	public void setProductTransactions(Set<ProductTransaction> productTransactionss) {
		this.productTransactions = productTransactionss;
	}

	private static final long serialVersionUID = 7091303478542316555L;
}
