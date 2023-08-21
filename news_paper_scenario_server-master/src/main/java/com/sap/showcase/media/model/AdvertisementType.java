package com.sap.showcase.media.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdvertisementType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long advertisementTypeId;
	private String description;
	private float price;
	
	public AdvertisementType() {
		
	}
	public AdvertisementType(Long advertisementTypeId, String description, float price) {
		super();
		this.advertisementTypeId = advertisementTypeId;
		this.description = description;
		this.price = price;		
	}
	public Long getAdvertisementTypeId() {
		return advertisementTypeId;
	}
	public void setAdvertisementTypeId(Long advertisementTypeId) {
		this.advertisementTypeId = advertisementTypeId;
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
}