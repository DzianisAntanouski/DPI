package com.sap.dpi.skeleton.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.dpi.skeleton.model.Customer;
import com.sap.dpi.skeleton.service.CustomerRepository;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public Collection<Customer> customers(@RequestParam Map<String, String> searchParameter) {
        return customerRepository.search(searchParameter);
    }

}