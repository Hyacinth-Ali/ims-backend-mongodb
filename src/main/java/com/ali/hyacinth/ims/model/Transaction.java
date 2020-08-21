package com.ali.hyacinth.ims.model;

import java.util.List;

import org.springframework.data.annotation.Id;
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
	@Id
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amountPaid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(amountUnpaid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (editable ? 1231 : 1237);
		temp = Double.doubleToLongBits(totalAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Transaction))
			return false;
		Transaction other = (Transaction) obj;
		if (Double.doubleToLongBits(amountPaid) != Double.doubleToLongBits(other.amountPaid))
			return false;
		if (Double.doubleToLongBits(amountUnpaid) != Double.doubleToLongBits(other.amountUnpaid))
			return false;
		if (editable != other.editable)
			return false;
		if (Double.doubleToLongBits(totalAmount) != Double.doubleToLongBits(other.totalAmount))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}

}
