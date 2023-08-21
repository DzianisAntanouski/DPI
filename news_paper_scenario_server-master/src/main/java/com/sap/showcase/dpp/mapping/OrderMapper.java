package com.sap.showcase.dpp.mapping;

import com.sap.showcase.dpp.model.DppOrder;
import com.sap.showcase.media.model.Orders;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    private OrderMapper() {
    }

    public static DppOrder mapToDppOrder(Orders order) {
        DppOrder dppOrder = new DppOrder();
        dppOrder.setOrderID(order.getOrder_ID());
        dppOrder.setCustomerID(order.getCustomerID());
        dppOrder.setCreatedAt(order.getCreatedAt());
        dppOrder.setOrderTitle(order.getOrderTitle());

        return dppOrder;
    }

    public static List<DppOrder> mapToDppOrder(List<Orders> orders) {
        return orders.stream().map(OrderMapper::mapToDppOrder).collect(Collectors.toList());
    }
}
