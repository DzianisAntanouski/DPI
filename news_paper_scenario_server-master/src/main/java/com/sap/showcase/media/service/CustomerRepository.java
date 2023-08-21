package com.sap.showcase.media.service;

import com.sap.showcase.media.model.Customer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	//@Query("SELECT c FROM Customer c WHERE c.isBlocked = 'false'")
    public List<Customer> findByCustomerID(Long customerID);
    public List<Customer> findByFirstName(String firtName);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByEmail(String email);
    public List<Customer> findByIsBlockedOrIsBlockedIsNull(boolean isBlocked);
}
