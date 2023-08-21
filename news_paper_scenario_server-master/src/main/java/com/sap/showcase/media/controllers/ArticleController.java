package com.sap.showcase.media.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sap.showcase.common.security.pii.PersonalDataEvent;
import com.sap.showcase.exception.NotFoundException;
import com.sap.showcase.exception.ResourceNotFoundException;
import com.sap.showcase.media.model.Article;
import com.sap.showcase.media.model.Edition;
import com.sap.showcase.media.model.Magazine;
import com.sap.showcase.media.service.ArticleService;

import static com.sap.showcase.media.controllers.ControllerConst.API_MGZN_PATH;

@RestController
public class ArticleController {	

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("articleID","description","author","edition");
    }

	@RequestMapping(API_MGZN_PATH+"/{magId}/editions/{editId}/articles/")
	public Object getAllArticle(@PathVariable Long magId, @PathVariable Long editId) {
		List<Article> articles = articleService.getArticles(editId);
		if(articles.size()==0){
			throw new ResourceNotFoundException("No Articles found for Magazine with id:"+magId);
		}else{
			publisher.publishEvent(new PersonalDataEvent("GET", articleService.getArticles(editId)));
			publisher.publishEvent(new PersonalDataEvent("MASK", articleService.getArticles(editId)));
		}
		return articles;		
	}

	@RequestMapping(API_MGZN_PATH+"/{magId}/editions/{editId}/articles/{artId}")
	public Article getArticle(@PathVariable Long artId) {
		Article article =  articleService.getArticle(artId);
		if(article==null ){
			throw new ResourceNotFoundException("No Article exists with Id:"+artId);
		}else{		
			publisher.publishEvent(new PersonalDataEvent("GET", articleService.getArticle(artId)));
			publisher.publishEvent(new PersonalDataEvent("MASK", articleService.getArticle(artId)));
		}		
		return article;
	}
	
	@RequestMapping(method=RequestMethod.POST,value=API_MGZN_PATH+"/{magId}/editions/{editId}/articles/")
	public void addArticle (@RequestBody Article article,  @PathVariable Long magId, @PathVariable Long editId) {
		Magazine magazine = new Magazine();
		magazine.setMagazineID(magId);
		
		Edition edition =  new Edition();
		edition.setEditionID(editId);
		edition.setMagazine(magazine);
		
		article.setEdition(edition);		
		articleService.addArticle(article);
	}
		
	@RequestMapping(method=RequestMethod.PUT,value=API_MGZN_PATH+"/{magId}/editions/{editId}/articles/{artId}")
	public void updateArticle (@RequestBody Article article, @PathVariable Long magId, @PathVariable Long editId, @PathVariable Long artId  ) {
		throwIfNonexisting(artId);
		
		Magazine magazine = new Magazine();
		magazine.setMagazineID(magId);
		
		Edition edition =  new Edition();
		edition.setEditionID(editId);
		edition.setMagazine(magazine);
		
		article.setEdition(edition);

		publisher.publishEvent(new PersonalDataEvent("PUT_M", article, articleService.getArticle(artId )));
		articleService.updateArticle(editId, article);
		publisher.publishEvent(new PersonalDataEvent("PUT_S", article, articleService.getArticle(artId )));
	}

	@RequestMapping(method=RequestMethod.DELETE,value=API_MGZN_PATH+"/{magId}/editions/{editId}/articles/{artId}")
	public void deleteArticle (@PathVariable Long magId, @PathVariable Long editId,@PathVariable  Long artId) {
		throwIfNonexisting(artId);
		articleService.deleteArticle(artId);		
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value=API_MGZN_PATH+"/{magId}/editions/{editId}/articles/")
	public void deleteAllArticles (@PathVariable Long editId) {
//		throwIfNonexisting(magId);
		articleService.deleteAllArticleByEditionID(editId);
	}
	
        
    private void throwIfNonexisting(long id) {
	    if (!articleService.exists(id)) {
	        NotFoundException notFoundException = new NotFoundException("Article not found id:"+id);
	        logger.warn("request failed", notFoundException);
	        throw notFoundException;
	    }
	}
}