package com.sap.showcase.media.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sap.showcase.exception.ResourceNotFoundException;
import com.sap.showcase.media.model.Customer;
import com.sap.showcase.media.model.Magazine;
import com.sap.showcase.media.model.Subscription;
import com.sap.showcase.media.model.SubscriptionType;
import com.sap.showcase.media.service.SubscriptionService;

import static com.sap.showcase.media.controllers.ControllerConst.API_CUST_PATH;
import static com.sap.showcase.media.controllers.ControllerConst.API_MGZN_PATH;

@RestController
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;
	private ResourceNotFoundException noSubscriptions = new ResourceNotFoundException("Subscription could not be found");

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("subscription_ID","customer","startDate","endDate","subscriptionType","magazine");
    }
	
	@RequestMapping(API_CUST_PATH+"/{custId}/subscriptions")
	public Object getAllSubscriptions(@PathVariable Long custId) {		
		List<Subscription> subscriptions = subscriptionService.getSubscriptions(custId);
		if (subscriptions.size()==0)
			throw noSubscriptions;
		return subscriptions;		
		
	}
	@RequestMapping(API_MGZN_PATH+"/{magId}/subscriptions")
	public Object getAllSubscriptionsByMagazineID(@PathVariable Long magId) {
		List<Subscription> subscriptions = subscriptionService.getSubscriptionsByMagazineID(magId);
		if (subscriptions.size()==0)
			throw noSubscriptions;		
		return subscriptions;		
	}
	
	@RequestMapping(API_CUST_PATH+"/{custId}/subscriptions/{id}")
	public Subscription getSubscription(@PathVariable Long id) {
		return subscriptionService.getSubscription(id);
	}

	@RequestMapping(method=RequestMethod.POST,value=API_CUST_PATH+"/{custId}/subscriptions/{subscriptionTypeId}/{magId}")
	public void addSubscription (@RequestBody Subscription subscription, @PathVariable Long custId, 
									@PathVariable Long subscriptionTypeId, @PathVariable Long magId) {
		Customer customer = new Customer();
		customer.setCustomerID(custId);
		subscription.setCustomer(customer);
		
		Magazine magazine = new Magazine();
		magazine.setMagazineID(magId);
		subscription.setMagazine(magazine);
		
		SubscriptionType subscriptionType = new SubscriptionType();
		subscriptionType.setSubscriptionTypeId(subscriptionTypeId);
		subscription.setSubscriptionType(subscriptionType);
		subscriptionService.addSubscription(subscription);		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value=API_CUST_PATH+"/{custId}/subscriptions/{subscriptionTypeId}/{magId}")
	public void updateSubscription (@RequestBody Subscription subscription,@PathVariable Long custId,@PathVariable Long subscriptionTypeId,@PathVariable Long magId) {
		Customer customer = new Customer();
		customer.setCustomerID(custId);
		subscription.setCustomer(customer);
		
		SubscriptionType subscriptionType = new SubscriptionType();
		subscriptionType.setSubscriptionTypeId(subscriptionTypeId);
		subscription.setSubscriptionType(subscriptionType);
		
		Magazine magazine = new Magazine();
		magazine.setMagazineID(magId);
		subscription.setMagazine(magazine);
		
		subscriptionService.updateSubscription(subscription);
	}

	@RequestMapping(method=RequestMethod.DELETE,value=API_CUST_PATH+"/{custId}/subscriptions/{id}")
	public void deleteSubscription (@PathVariable Long id) {
		 subscriptionService.deleteSubscription(id);		
	}
}