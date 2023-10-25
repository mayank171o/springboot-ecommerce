package com.fullstack.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fullstack.ecommerce.dao.CustomerRepository;
import com.fullstack.ecommerce.dto.PaymentInfo;
import com.fullstack.ecommerce.dto.Purchase;
import com.fullstack.ecommerce.dto.PurchaseResponse;
import com.fullstack.ecommerce.entity.Customer;
import com.fullstack.ecommerce.entity.Order;
import com.fullstack.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	private CustomerRepository customerRepository;

	public CheckoutServiceImpl(CustomerRepository customerRepository, @Value("${stripe.key.secret}") String secretKey) {

		System.out.println("inside service impl constructor");
		this.customerRepository = customerRepository;
		// initialize Stripe API with secret key
		Stripe.apiKey = secretKey;
	}

	@Override
	public PurchaseResponse placeOrder(Purchase purchase) {

		// retrieve the order info from dto
		Order order = purchase.getOrder();

		// generate Tracking number
		String trackkingNumber = UUID.randomUUID().toString();
		order.setOrderTrackingNumber(trackkingNumber);

		// populate order with order item

		Set<OrderItem> orderItems = purchase.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			order.add(orderItem);
		}

		order.setBillingAddress(purchase.getBillingAddress());
		order.setShippingAddress(purchase.getShippingAddress());

		// populate customer with Order
		Customer customer = purchase.getCustomer();
		Customer customerFromDB = customerRepository.findByEmail(customer.getEmail());
		if (customerFromDB != null) {
			customer = customerFromDB;
		}
		customer.add(order);

		// save to the database
		customerRepository.save(customer);

		// Return a response

		return new PurchaseResponse(trackkingNumber);
	}

	@Override
	public PaymentIntent createPaymentIntent(PaymentInfo pamentInfo) throws StripeException {

		List<String> paymentMethodTypes = new ArrayList<String>();
		paymentMethodTypes.add("card");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", pamentInfo.getAmount());
		params.put("currency", pamentInfo.getCurrency());
		params.put("payment_method_types", paymentMethodTypes);
		params.put("description", "Luv2ShopPurchase");
		params.put("receipt_email", pamentInfo.getReceiptEmail());
		return PaymentIntent.create(params);

	}

}
