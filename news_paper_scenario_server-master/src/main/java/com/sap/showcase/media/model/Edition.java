package com.sap.showcase.media.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.core.style.ToStringCreator;

@Entity
public class Edition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "editionID")
	private Long editionID;		
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "magazineID")
	private Magazine magazine;
	
	public Edition() {	
	}

	public Edition(Long editionID, String description) {
		super();
		this.editionID = editionID;
		this.description = description;
	}

	public Long getEditionID() {
		return editionID;
	}

	public void setEditionID(Long editionID) {
		this.editionID = editionID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	public Long getMagazineID() {
		return magazine.getMagazineID();
	}

	public void setMagazineID(Long magaizneID) {
		magazine.setMagazineID(magaizneID);
	}	
	
	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
		.append("editionID", this.editionID)
		.append("description", this.description)		
		.toString();
	}
}
