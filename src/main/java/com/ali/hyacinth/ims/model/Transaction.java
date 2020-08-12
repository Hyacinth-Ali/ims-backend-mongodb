package com.ali.hyacinth.ims.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {
	private boolean editable;

	public void setEditable(boolean value) {
		this.editable = value;
	}

	public boolean isEditable() {
		return this.editable;
	}

	private double totalAmount;

	public void setTotalAmount(double value) {
		this.totalAmount = value;
	}

	@Column(nullable = false, unique = false)
	public double getTotalAmount() {
		return this.totalAmount;
	}

	private double amountPaid;

	public void setAmountPaid(double value) {
		this.amountPaid = value;
	}

	@Column(nullable = false)
	public double getAmountPaid() {
		return this.amountPaid;
	}

	private Employee seller;

	@ManyToOne(optional = false)
	public Employee getSeller() {
		return this.seller;
	}

	public void setSeller(Employee seller) {
		this.seller = seller;
	}

	private Customer buyer;

	@ManyToOne(optional = false)
	public Customer getBuyer() {
		return this.buyer;
	}

	public void setBuyer(Customer buyer) {
		this.buyer = buyer;
	}

	private List<ProductTransaction> productTransactions;

	@OneToMany(mappedBy = "transaction", cascade = { CascadeType.ALL })
	public List<ProductTransaction> getProductTransactions() {
		return this.productTransactions;
	}

	public void setProductTransactions(List<ProductTransaction> productTransactionss) {
		this.productTransactions = productTransactionss;
	}

	private double amountUnpaid;

	public void setAmountUnpaid(double amountUnpaid) {
		this.amountUnpaid = amountUnpaid;
	}

	@Column(nullable = true)
	public double getAmountUnpaid() {
		amountUnpaid = this.totalAmount - this.amountPaid;
		return amountUnpaid;
	}

	private String transactionDate;

	public void setTransactionDate(String value) {
		this.transactionDate = value;
	}

	@Column(nullable = true)
	public String getTransactionDate() {
		return this.transactionDate;
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

	private String transactionId;

	public void setTransactionId(String value) {
		this.transactionId = value;
	}

	@Column(unique = true, nullable = false)
	public String getTransactionId() {
		return this.transactionId;
	}

	private static final long serialVersionUID = -8943418603226944904L;
}
