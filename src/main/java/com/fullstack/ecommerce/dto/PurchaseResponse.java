package com.fullstack.ecommerce.dto;

import lombok.Data;

@Data
public class PurchaseResponse {

	private String orderTrackingNumber;

	public PurchaseResponse(String trackkingNumber) {
		this.orderTrackingNumber = trackkingNumber;
	}

	public String getOrderTrackingNumber() {
		return orderTrackingNumber;
	}

	public void setOrderTrackingNumber(String orderTrackingNumber) {
		this.orderTrackingNumber = orderTrackingNumber;
	}
	
	
}
