package com.sap.showcase.media.service;

import com.sap.showcase.media.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;

	public List<Orders> getOrders(Long customerID){
		List<Orders> ordersToReturn = ordersRepository.findByCustomerCustomerID(customerID).stream().filter(orders -> !orders.getBlocked()).collect(Collectors.toList());
		return ordersToReturn;
	}
	
	public Orders getOrder(Long id) {
		Orders order = ordersRepository.findById(id).get();
		if(!order.getIsBlocked())
			return order;
		else
			return null;
	}

	public void addOrders(Orders Orders) {
		ordersRepository.save(Orders);
	}

	public void updateOrders(Long id, Orders Orders) {
		ordersRepository.save(Orders);
	}

	public void deleteOrders(Long id) {
		ordersRepository.deleteById(id);
	}
	
	public void deleteAllOrders() {
		ordersRepository.deleteAll();
	}
	
	public void deleteAllOrdersByCustomerID(Long id) {
		for(Orders orders : ordersRepository.findByCustomerCustomerID(id) ){
			ordersRepository.deleteById(orders.getOrder_ID());
		}
	}
	
	public boolean exists(Long id) {
		return ordersRepository.existsById(id);
		
	}

}
