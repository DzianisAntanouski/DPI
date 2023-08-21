package com.sap.showcase.media.service;

import com.sap.showcase.media.model.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<Orders, Long>{	
	public List<Orders> findByCustomerCustomerID(Long customerID);
	public List<Orders> findByIsBlockedOrIsBlockedIsNull(boolean isBlocked);
}
