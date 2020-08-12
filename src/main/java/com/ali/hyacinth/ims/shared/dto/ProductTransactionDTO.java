package com.ali.hyacinth.ims.shared.dto;

public class ProductTransactionDTO {
	private String productName;
	private double price;
	private int quantity;
	private String pTransactionId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getpTransactionId() {
		return pTransactionId;
	}

	public void setpTransactionId(String pTransactionId) {
		this.pTransactionId = pTransactionId;
	}

}
