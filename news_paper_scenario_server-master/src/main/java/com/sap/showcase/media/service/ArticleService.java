package com.sap.showcase.media.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.sap.showcase.media.model.Article;

@Service
@Validated
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;

	public List<Article> getArticles(Long editionID){
		List<Article> Article = new ArrayList<>();		
		articleRepository.findByEditionEditionID(editionID).forEach(Article::add);
		return Article;
	}
	
	public Article getArticle(Long id) {
		return articleRepository.findById(id).get();
	}

	public void addArticle(Article article) {
		articleRepository.save(article);		
	}

	public void updateArticle(Long id, Article article) {
		articleRepository.save(article);		
	}

	public void deleteArticle(Long id) {
		articleRepository.deleteById(id);
	}
	
	public void deleteAllArticle() {
		articleRepository.deleteAll();
	}
	
	public void deleteAllArticleByEditionID(Long id) {
		for(Article article : articleRepository.findByEditionEditionID(id) ){
			articleRepository.deleteById(article.getArticleID());
		}
	}
	
	public boolean exists(Long id) {
		return articleRepository.existsById(id);
	}

}
