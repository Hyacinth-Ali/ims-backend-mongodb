package com.ali.hyacinth.ims.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productTransactions")
public class ProductTransaction {
	private double price;
	@DBRef(lazy = true)
	private Product product;
	@DBRef(lazy = true)
	private Transaction transaction;
	@Id
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pTransactionId == null) ? 0 : pTransactionId.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProductTransaction))
			return false;
		ProductTransaction other = (ProductTransaction) obj;
		if (pTransactionId == null) {
			if (other.pTransactionId != null)
				return false;
		} else if (!pTransactionId.equals(other.pTransactionId))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}
	
	

}
