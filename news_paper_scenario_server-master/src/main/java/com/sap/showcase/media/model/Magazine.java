package com.sap.showcase.media.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.core.style.ToStringCreator;

@Entity
public class Magazine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "magazineID")
	private Long magazineID;
	private String description;
	
	public Magazine() {		
	}

	public Magazine(Long magazineID, String description) {
		super();
		this.magazineID = magazineID;
		this.description = description;
	}

	public Long getMagazineID() {
		return magazineID;
	}

	public void setMagazineID(Long magazineID) {
		this.magazineID = magazineID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("magazineID", this.magazineID)
				.append("description", this.description)				
				.toString();
	}	
}
