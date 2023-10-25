package com.fullstack.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.ecommerce.dto.PaymentInfo;
import com.fullstack.ecommerce.dto.Purchase;
import com.fullstack.ecommerce.dto.PurchaseResponse;
import com.fullstack.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@CrossOrigin("https://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;

	@PostMapping("/purchase")
	public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
		System.out.println("post mapping");	
		PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
		return purchaseResponse;

		
	}
	
	@PostMapping("/payment-intent")
	public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException
	{
		PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);
		
		String paymentStr = paymentIntent.toJson();
		System.out.println("payment str is " + paymentStr);
		return new ResponseEntity<String>(paymentStr,HttpStatus.OK);
		
	}
}
