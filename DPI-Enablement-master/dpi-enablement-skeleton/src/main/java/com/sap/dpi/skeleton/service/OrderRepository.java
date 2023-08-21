package com.sap.dpi.skeleton.service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.sap.dpi.skeleton.model.Order;

@Service
public class OrderRepository {

    private final Map<Integer, Order> database;

    public OrderRepository() {
        database = new ConcurrentHashMap<>();
    }

    public Collection<Order> getAll() {
        return database.values();
    }

    public Optional<Order> get(Integer orderID) {
        return Optional.of(database.get(orderID));
    }

    public Optional<Order> upsert(Order order) {
        if (order != null && order.getOrderID() != null) {
            database.put(order.getOrderID(), order);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    public void delete(Integer orderID) {
        database.remove(orderID);
    }

    /**
     * search allowed only with customerID, other search parameters are ignored
     */
    public Collection<Order> search(Map<String, String> searchParameter) {
        Stream<Order> orders = getAll().stream();

        if (searchParameter.containsKey("customerID")) {
            String customerID = searchParameter.get("customerID");
            orders = orders.filter(o -> o.getCustomerID().equals(customerID));
        }
        return orders.collect(Collectors.toList());
    }

}
