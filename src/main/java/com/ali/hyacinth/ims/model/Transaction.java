package com.ali.hyacinth.ims.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
public class Transaction {
	private boolean editable;
	private double totalAmount;
	private double amountPaid;
	@DBRef
	private Employee seller;
	@DBRef
	private Customer buyer;
	@DBRef
	private List<ProductTransaction> productTransactions;
	private double amountUnpaid;
	private String transactionDate;
	private long id;
	private String transactionId;

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Employee getSeller() {
		return seller;
	}

	public void setSeller(Employee seller) {
		this.seller = seller;
	}

	public Customer getBuyer() {
		return buyer;
	}

	public void setBuyer(Customer buyer) {
		this.buyer = buyer;
	}

	public List<ProductTransaction> getProductTransactions() {
		return productTransactions;
	}

	public void setProductTransactions(List<ProductTransaction> productTransactions) {
		this.productTransactions = productTransactions;
	}

	public double getAmountUnpaid() {
		return amountUnpaid;
	}

	public void setAmountUnpaid(double amountUnpaid) {
		this.amountUnpaid = amountUnpaid;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
