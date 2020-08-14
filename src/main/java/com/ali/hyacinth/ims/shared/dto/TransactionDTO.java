package com.ali.hyacinth.ims.shared.dto;

public class TransactionDTO {
	
	private double totalAmount;
	private double amountPaid;
	private double amountUnpaid;
	private String date;
	private String transactionId;
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
	public double getAmountUnpaid() {
		return amountUnpaid;
	}
	public void setAmountUnpaid(double amoundUnpaid) {
		this.amountUnpaid = amoundUnpaid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
//	public long getId() {
//		return id;
//	}
//	public void setId(long id) {
//		this.id = id;
//	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
}
