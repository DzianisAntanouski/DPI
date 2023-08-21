package com.sap.showcase.media.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.showcase.media.model.SubscriptionType;

@Service
public class SubscriptionTypeService {
	@Autowired
	private SubscriptionTypeRepository subscriptTypeRepository;
	


	public List<SubscriptionType> getSubscriptionTypes(){
		List<SubscriptionType> subscriptTypes = new ArrayList<>();
		subscriptTypeRepository.findAll().forEach(subscriptTypes::add);
		return subscriptTypes;
	}
	
	public SubscriptionType getsubscriptionType(Long id) {
		return subscriptTypeRepository.findById(id).get();
	}

	public void addSubscriptionType(SubscriptionType subscriptionType) {
		subscriptTypeRepository.save(subscriptionType);
		
	}


	public void updateSubscriptionType(Long id, SubscriptionType subscriptionType) {
		subscriptionType.setSubscriptionTypeId(id);
		subscriptTypeRepository.save(subscriptionType);		
	}

	public void deleteSubscriptionType(Long id) {
		subscriptTypeRepository.deleteById(id);
	}

	public void deleteAllSubscriptionTypes() {
		subscriptTypeRepository.deleteAll();
	}
}
