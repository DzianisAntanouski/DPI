package com.sap.showcase.media.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class SubscriptionType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscriptionTypeId;
	private String description;
	private float price;
	private boolean oneTime;
	public SubscriptionType() {
		
	}
	public SubscriptionType(Long subscriptionTypeId, String description, float price, boolean oneTime) {
		super();
		this.subscriptionTypeId = subscriptionTypeId;
		this.description = description;
		this.price = price;
		this.oneTime = oneTime;
	}
	public Long getSubscriptionTypeId() {
		return subscriptionTypeId;
	}
	public void setSubscriptionTypeId(Long subscriptionTypeId) {
		this.subscriptionTypeId = subscriptionTypeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isOneTime() {
		return oneTime;
	}
	public void setOneTime(boolean oneTime) {
		this.oneTime = oneTime;
	}
	
	
	
}
