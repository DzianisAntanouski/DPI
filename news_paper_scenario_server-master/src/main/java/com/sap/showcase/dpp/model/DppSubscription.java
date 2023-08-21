package com.sap.showcase.dpp.model;

import java.util.Date;
import java.util.Objects;

public class DppSubscription {
    private Long subscriptionID;
    private Long customerID;
    private Date startDate;
    private Date endDate;
    private Long subscriptionTypeId;
    private Long magazineID;
	private Boolean isBlocked = false;

    public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Long getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(Long subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getSubscriptionTypeId() {
        return subscriptionTypeId;
    }

    public void setSubscriptionTypeId(Long subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }

    public Long getMagazineID() {
        return magazineID;
    }

    public void setMagazineID(Long magazineID) {
        this.magazineID = magazineID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DppSubscription that = (DppSubscription) o;
        return Objects.equals(subscriptionID, that.subscriptionID) &&
                Objects.equals(customerID, that.customerID) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(subscriptionTypeId, that.subscriptionTypeId) &&
                Objects.equals(magazineID, that.magazineID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionID, customerID, startDate, endDate, subscriptionTypeId, magazineID);
    }

    @Override
    public String toString() {
        return "DppSubscription{" +
                "subscription_ID=" + subscriptionID +
                ", customerID=" + customerID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", subscriptionTypeId=" + subscriptionTypeId +
                ", magazineID=" + magazineID +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
