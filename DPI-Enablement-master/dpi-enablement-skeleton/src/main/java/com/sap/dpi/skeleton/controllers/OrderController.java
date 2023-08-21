package com.sap.dpi.skeleton.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.dpi.skeleton.model.Order;
import com.sap.dpi.skeleton.service.OrderRepository;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public Collection<Order> getOrders(@RequestParam Map<String, String> searchParameter) {
        return orderRepository.search(searchParameter);
    }
}
