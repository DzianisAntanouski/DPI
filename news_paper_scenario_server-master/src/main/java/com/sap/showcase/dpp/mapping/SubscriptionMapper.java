package com.sap.showcase.dpp.mapping;

import com.sap.showcase.dpp.model.DppSubscription;
import com.sap.showcase.media.model.Subscription;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionMapper {
    private SubscriptionMapper() {
    }

    public static DppSubscription mapToDppSubscription(Subscription subscription) {
        DppSubscription dppSubscription = new DppSubscription();
        dppSubscription.setSubscriptionID(subscription.getSubscription_ID());
        dppSubscription.setCustomerID(subscription.getCustomerID());
        dppSubscription.setStartDate(subscription.getStartDate());
        dppSubscription.setEndDate(subscription.getEndDate());
        dppSubscription.setSubscriptionTypeId(subscription.getSubscriptionType().getSubscriptionTypeId());
        dppSubscription.setMagazineID(subscription.getMagazineID());

        return dppSubscription;
    }

    public static List<DppSubscription> mapToDppSubscription(List<Subscription> subscriptions) {
        return subscriptions.stream().map(SubscriptionMapper::mapToDppSubscription).collect(Collectors.toList());
    }
}
