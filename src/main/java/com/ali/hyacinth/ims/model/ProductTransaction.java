package com.ali.hyacinth.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "productTransactions")
public class ProductTransaction implements Serializable {
	private double price;

	public void setPrice(double value) {
		this.price = value;
	}

	@Column(nullable = false)
	public double getPrice() {
		return this.price;
	}

	private Product product;

	@ManyToOne(optional = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private Transaction transaction;

	@ManyToOne(optional = false)
	@JoinColumn(name = "transaction_id")
	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
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

	private String pTransactionId;

	public void setPTransactionId(String value) {
		this.pTransactionId = value;
	}

	@Column(unique = true, nullable = false)
	public String getPTransactionId() {
		return this.pTransactionId;
	}

	private int quantity;

	public void setQuantity(int value) {
		this.quantity = value;
	}

	@Column(nullable = false)
	public int getQuantity() {
		return this.quantity;
	}

	private static final long serialVersionUID = -7449099535184796784L;
}
