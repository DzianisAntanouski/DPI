package com.sap.showcase.dpp.model;

import java.sql.Timestamp;
import java.util.Objects;

public class DppOrder {
    private Long orderID;
    private Long customerID;
    private Timestamp createdAt;
    private String orderTitle;
	private Boolean isBlocked = false;

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DppOrder dppOrder = (DppOrder) o;
        return Objects.equals(orderID, dppOrder.orderID) &&
                Objects.equals(customerID, dppOrder.customerID) &&
                Objects.equals(createdAt, dppOrder.createdAt) &&
                Objects.equals(orderTitle, dppOrder.orderTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, customerID, createdAt, orderTitle);
    }

    @Override
    public String toString() {
        return "DppOrder{" +
                "orderID=" + orderID +
                ", customerID=" + customerID +
                ", createdAt=" + createdAt +
                ", orderTitle='" + orderTitle + '\'' +
                ", isBlocked='" + isBlocked + '\'' +
                '}';
    }

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}
