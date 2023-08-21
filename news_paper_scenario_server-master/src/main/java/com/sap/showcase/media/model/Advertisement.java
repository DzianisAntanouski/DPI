package com.sap.showcase.media.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sap.showcase.media.model.AdvertisementType;

@Entity
public class Advertisement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long advertisement_ID;
	
	private String description;
	private String author;
	@OneToOne()
	@JoinColumn(name="advertisementTypeId",nullable=false)
	private AdvertisementType advertisementType;
	
	@ManyToOne()
	@JoinColumn(name="magazineID",nullable=false)
	private Magazine magazine;
	
	public Advertisement() {
		
	}
	public Advertisement(Long advertisement_ID, String description, String author, Long advertisementTypeId) {
		super();
		this.advertisement_ID = advertisement_ID;
		this.description = description;
		this.author = author;		
		this.advertisementType = new AdvertisementType();
		this.advertisementType.setAdvertisementTypeId(advertisementTypeId);		
	}
	
	public AdvertisementType getAdvertisementType() {
		return advertisementType;
	}
	public void setAdvertisementType(AdvertisementType advertisementType) {
		this.advertisementType = advertisementType;
	}
	
	public String getAuthor() {
		return author;
	}
	public String getDescription() {
		return description;
	}
	
	public Long getAdvertisement_ID() {
		return advertisement_ID;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAdvertisement_ID(Long advertisement_ID) {
		this.advertisement_ID = advertisement_ID;
	}
	public Long getMagazineID() {
		return magazine.getMagazineID();
	}
	public void setMagazineID(Long magazineID) {
		magazine.setMagazineID(magazineID);;
	}
	public Magazine getMagazine() {
		return magazine;
	}
	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}	
	
}