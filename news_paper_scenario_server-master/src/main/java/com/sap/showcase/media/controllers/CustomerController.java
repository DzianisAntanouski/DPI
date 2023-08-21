package com.sap.showcase.media.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sap.showcase.common.security.pii.PersonalDataEvent;
import com.sap.showcase.exception.ResourceNotFoundException;
import com.sap.showcase.media.model.Customer;
import com.sap.showcase.media.service.CustomerService;

import static com.sap.showcase.media.controllers.ControllerConst.API_CUST_PATH;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("customerID","firstName", "lastName","email","phone","address","birthday");
    }
	
	@RequestMapping(API_CUST_PATH)
	public Object getAllCustomers() {		 
		List<Customer> customers = customerService.getCustomers();
		if (customers.size()==0){
			throw new ResourceNotFoundException("No Customers found");
		}else{
			publisher.publishEvent(new PersonalDataEvent("GET", customers));
			publisher.publishEvent(new PersonalDataEvent("MASK", customers));
		}
		return customers;		
	}
	@RequestMapping(API_CUST_PATH+"/{id}")
	public Customer getCustomer(@PathVariable Long id) {		
		Customer customer = customerService.getCustomer(id);
		if (customer==null ){
			throw new ResourceNotFoundException("No Customer exists with Id:"+id);
		}
		else{
			publisher.publishEvent(new PersonalDataEvent("GET", customer));
			publisher.publishEvent(new PersonalDataEvent("MASK", customer));
		}
		
		return customer;
	}
	@RequestMapping(method=RequestMethod.POST,value=API_CUST_PATH)
	public void addCustomer (@RequestBody Customer customer) {
		customerService.addCustomer(customer);		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value=API_CUST_PATH+"/{id}")	
	public void updateCustomer (@RequestBody Customer customer, @PathVariable Long id) {
		if (id==null){
			throw new ResourceNotFoundException("Cannot Update as no such customer exists:"+id);
		}
		publisher.publishEvent(new PersonalDataEvent("PUT_M", customerService.getCustomer(id), customer));
		customerService.updateCustomer(id,customer);
		publisher.publishEvent(new PersonalDataEvent("PUT_S", customerService.getCustomer(id), customer));		
	}


	@RequestMapping(method=RequestMethod.DELETE,value=API_CUST_PATH+"/{id}")
	public void deleteCustomer (@PathVariable Long id) {
		customerService.deleteCustomer(id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value=API_CUST_PATH)
	public void deleteAllCustomers() {
		 customerService.deleteAllCustomers();		
	}		
}