package com.sap.dpi.skeleton.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.dpi.skeleton.model.Subscription;
import com.sap.dpi.skeleton.service.SubscriptionRepository;

@RestController
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/subscriptions")
    public Collection<Subscription> getSubscriptions(@RequestParam Map<String, String> searchParameter) {
        return subscriptionRepository.search(searchParameter);
    }
}
