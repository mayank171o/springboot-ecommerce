package com.fullstack.ecommerce.dto;

import lombok.Data;


public class PaymentInfo {

	private int amount;
	private String currency;
	private String receiptEmail;

	public PaymentInfo(int amount, String currency ,String email) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.receiptEmail = email;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReceiptEmail() {
		return receiptEmail;
	}

	public void setReceiptEmail(String receiptEmail) {
		this.receiptEmail = receiptEmail;
	}
	

}
