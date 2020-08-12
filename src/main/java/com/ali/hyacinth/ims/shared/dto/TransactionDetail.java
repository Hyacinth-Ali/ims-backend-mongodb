package com.ali.hyacinth.ims.shared.dto;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetail {

	// Receipt Attributes
	private String transactionId;
	private String firstName;
	private String lastName;
	private String customerName;
	private String phoneNumber;
	private String date;
	private double totalAmount;
	private double amountPaid;
	private double amountUnpaid;
	private String itemPrice;

	// Receipt Associations
	private List<ProductTransactionDTO> pTransactions;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public TransactionDetail() {
		customerName = null;
		date = null;
		totalAmount = 0;
		amountPaid = 0;
		pTransactions = new ArrayList<ProductTransactionDTO>();
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String productName) {
		this.customerName = productName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public List<ProductTransactionDTO> getpTransactions() {
		return pTransactions;
	}

	public void setpTransactions(List<ProductTransactionDTO> pTransactions) {
		this.pTransactions = pTransactions;
	}

	public double getAmountUnpaid() {
		return amountUnpaid;
	}

	public void setAmountUnpaid(double amountUnpaid) {
		this.amountUnpaid = amountUnpaid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	

}
