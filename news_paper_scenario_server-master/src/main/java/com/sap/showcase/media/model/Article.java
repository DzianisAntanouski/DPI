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
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "articleID")
	private Long articleID;		
	private String description;
	private String author;
	
	@ManyToOne
	@JoinColumn(name = "editionID")
	private Edition edition;
	
	public Article() {	
	}

	public Article(Long articleID, String description) {
		super();
		this.articleID = articleID;
		this.description = description;
	}

	public Long getArticleID() {
		return articleID;
	}

	public void setArticleID(Long articleID) {
		this.articleID = articleID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	public Long getEditionID() {
		return edition.getEditionID();
	}

	public void setEditionID(Long magaizneID) {
		edition.setEditionID(magaizneID);
	}	
	
	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
		.append("articleID", this.articleID)
		.append("description", this.description)		
		.append("author", this.author)
		.toString();
	}
}
