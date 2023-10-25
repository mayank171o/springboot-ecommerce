package com.fullstack.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fullstack.ecommerce.entity.Customer;

@CrossOrigin("https://localhost:4200")
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Customer findByEmail(String theEmail);
	

}
