package com.sap.dpi.skeleton;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.dpi.skeleton.model.Customer;
import com.sap.dpi.skeleton.model.Order;
import com.sap.dpi.skeleton.model.Subscription;
import com.sap.dpi.skeleton.service.CustomerRepository;
import com.sap.dpi.skeleton.service.OrderRepository;
import com.sap.dpi.skeleton.service.SubscriptionRepository;

@Component
public class InitializeCustomers implements CommandLineRunner {

    @Value("classpath:data/customers.json")
    private Resource customerData;

    @Value("classpath:data/orders.json")
    private Resource orderData;

    @Value("classpath:data/subscriptions.json")
    private Resource subscriptionData;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCustomers();
        loadOrders();
        loadSubscriptions();
    }

    private void loadCustomers() throws IOException {
        JsonNode root = new ObjectMapper().readValue(customerData.getFile(), JsonNode.class);
        ObjectMapper mapper = new ObjectMapper();
        Customer[] customers = mapper.treeToValue(root, Customer[].class);
        for (Customer customer : customers) {
            customerRepository.upsert(customer);
        }
    }

    private void loadOrders() throws IOException {
        JsonNode root = new ObjectMapper().readValue(orderData.getFile(), JsonNode.class);
        Order[] orders = new ObjectMapper().treeToValue(root, Order[].class);
        for (Order order : orders) {
            orderRepository.upsert(order);
        }
    }

    private void loadSubscriptions() throws IOException {
        JsonNode root = new ObjectMapper().readValue(subscriptionData.getFile(), JsonNode.class);
        Subscription[] subscriptions = new ObjectMapper().treeToValue(root, Subscription[].class);
        for (Subscription subscription : subscriptions) {
            subscriptionRepository.upsert(subscription);
        }
    }
}
