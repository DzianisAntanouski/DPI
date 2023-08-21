package com.sap.showcase.media.service;

import com.sap.showcase.media.model.Subscription;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long>{
	public List<Subscription> findByCustomerCustomerID(Long customerID);
	public List<Subscription> findByMagazineMagazineID(Long magazineID);
	public List<Subscription> findByIsBlockedOrIsBlockedIsNull(boolean isBlocked);

}
