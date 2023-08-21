package com.sap.showcase.media.service;

import com.sap.showcase.media.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
	@Autowired
	private SubscriptionRepository subscriptionRepository;

	public List<Subscription> getSubscriptions(Long customerID) {
		List<Subscription> subscriptions = subscriptionRepository.findByCustomerCustomerID(customerID).stream()
				.filter(subscription -> !subscription.getBlocked()).collect(Collectors.toList());
		return subscriptions;
	}

	public List<Subscription> getSubscriptionsByMagazineID(Long magazineID) {
		List<Subscription> subscriptions = new ArrayList<>();
		subscriptionRepository.findByMagazineMagazineID(magazineID).forEach(subscriptions::add);
		return subscriptions;
	}

	public Subscription getSubscription(Long id) {
		Subscription subscription = subscriptionRepository.findById(id).get();
		if (!subscription.getIsBlocked())
			return subscription;
		else
			return null;
	}

	public void addSubscription(Subscription subscription) {
		subscriptionRepository.save(subscription);

	}

	public void updateSubscription(Subscription subscription) {
		subscriptionRepository.save(subscription);
	}

	public void deleteSubscription(Long id) {
		subscriptionRepository.deleteById(id);
	}

	public void deleteAllSubscriptions() {
		subscriptionRepository.deleteAll();
	}

}
