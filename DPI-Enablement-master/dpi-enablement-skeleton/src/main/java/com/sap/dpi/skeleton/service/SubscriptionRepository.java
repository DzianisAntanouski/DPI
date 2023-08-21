package com.sap.dpi.skeleton.service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.sap.dpi.skeleton.model.Subscription;

@Service
public class SubscriptionRepository {

    private final Map<Integer, Subscription> database;

    public SubscriptionRepository() {
        database = new ConcurrentHashMap<>();
    }

    public Collection<Subscription> getAll() {
        return database.values();
    }

    public Optional<Subscription> get(Integer subscriptionID) {
        return Optional.of(database.get(subscriptionID));
    }

    public Optional<Subscription> upsert(Subscription subscription) {
        if (subscription != null && subscription.getSubscriptionID() != null) {
            database.put(subscription.getSubscriptionID(), subscription);
            return Optional.of(subscription);
        }
        return Optional.empty();
    }

    public void delete(Integer subscriptionID) {
        database.remove(subscriptionID);
    }

    public Collection<Subscription> search(Map<String, String> searchParameter) {
        Stream<Subscription> subscriptions = getAll().stream();

        if (searchParameter.containsKey("customerID")) {
            String customerID = searchParameter.get("customerID");
            subscriptions = subscriptions.filter(o -> o.getCustomerID().equals(customerID));
        }
        return subscriptions.collect(Collectors.toList());
    }
}
