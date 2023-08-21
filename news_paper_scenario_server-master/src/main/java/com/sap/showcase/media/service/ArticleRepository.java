package com.sap.showcase.media.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sap.showcase.media.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Long>{
	public List<Article> findByEditionEditionID(Long editionID);
}
