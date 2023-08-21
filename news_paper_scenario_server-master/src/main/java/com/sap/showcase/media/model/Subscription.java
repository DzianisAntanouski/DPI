package com.sap.showcase.media.model;

import javax.persistence.*;

import org.springframework.core.style.ToStringCreator;

import java.util.Date;

@Entity
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscription_ID;
	@ManyToOne()
	@JoinColumn(name="customerID",nullable=false)
//	@JsonBackReference
	private Customer customer;
	private Date startDate;
	private Date endDate;
	@OneToOne()
	@JoinColumn(name="subscriptionTypeId",nullable=false)
	private SubscriptionType subscriptionType;
	@ManyToOne()
	@JoinColumn(name="magazineID",nullable=false)
	private Magazine magazine;

	@Column(nullable = true)
	private Boolean isBlocked = false;

	public Subscription() {
		
	}
	public Subscription(Long customerId, Long subscription_ID, Date startDate, Date endDate, Long subscriptionTypeId, boolean isBlocked) {
		super();
		this.subscription_ID = subscription_ID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.customer = new Customer();
		this.customer.setCustomerID(customerId);
		this.subscriptionType = new SubscriptionType();
		this.subscriptionType.setSubscriptionTypeId(subscriptionTypeId);
		this.isBlocked = isBlocked;
	}
	
	public Boolean getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}
	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	public Long getCustomerID() {
		return customer.getCustomerID();
	}
	public Date getEndDate() {
		return endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Long getSubscription_ID() {
		return subscription_ID;
	}
	public void setCustomerID(Long customerId) {
		customer.setCustomerID(customerId);
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setSubscription_ID(Long subscription_ID) {
		this.subscription_ID = subscription_ID;
	}
	public Long getMagazineID() {
		return magazine.getMagazineID();
	}
	public void setMagazineID(Long magazineID) {
		magazine.setMagazineID(magazineID);
	}
	public Magazine getMagazine() {
		return magazine;
	}
	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}

	public boolean getBlocked() {
		return isBlocked != null && isBlocked;
	}

	public void setBlocked(Boolean blocked) {
		isBlocked = blocked;
	}
	@Override
	public String toString() {
        return new ToStringCreator(this)
        		.append("subscription_ID", this.subscription_ID)
        		.append("customerID", this.getCustomerID())
        		.append("startDate", this.startDate)
        		.append("endDate", this.endDate)
        		.append("subscriptionTypeId", this.getSubscription_ID())
        		.append("magazineID", this.getMagazineID())
        		.append("isBlocked",this.isBlocked)
        		.toString();
	}
	
}
