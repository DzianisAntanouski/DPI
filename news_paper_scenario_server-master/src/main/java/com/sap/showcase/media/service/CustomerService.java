package com.sap.showcase.media.service;

import com.sap.showcase.media.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getCustomers(){
		List<Customer> customers = new ArrayList<>();
		customerRepository.findByIsBlockedOrIsBlockedIsNull(false).forEach(customers::add);
		return customers;
	}
	
	public Customer getCustomer(Long id) {
		Customer customer = customerRepository.findById(id).get();
		if(!customer.isBlocked())
			return customer;
		else
			return null;
				
	}

	public void addCustomer(Customer customer) {
		customerRepository.save(customer);
		
	}


	public void updateCustomer(Long id, Customer customer) {
		customerRepository.save(customer);		
	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
		}
	
	public void deleteAllCustomers() {
		customerRepository.deleteAll();
		}

	public boolean exists(long id) {
		customerRepository.existsById(id);
		return false;
	}

}
