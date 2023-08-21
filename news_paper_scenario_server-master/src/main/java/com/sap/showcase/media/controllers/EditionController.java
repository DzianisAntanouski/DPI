package com.sap.showcase.media.controllers;

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
import com.sap.showcase.media.model.Edition;
import com.sap.showcase.media.model.Magazine;
import com.sap.showcase.media.service.EditionService;

import static com.sap.showcase.media.controllers.ControllerConst.API_MGZN_PATH;

import java.util.List;

@RestController
public class EditionController {	

	@Autowired
	private EditionService editionService;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("description","magazineID");
    }
	
	@RequestMapping(API_MGZN_PATH+"/{magId}/editions/")
	public Object getAllEdition(@PathVariable Long magId) {
		List<Edition> editions = editionService.getEditions(magId);
		if (editions.size()==0){
			throw new ResourceNotFoundException("No Editions found for Magazine with id:"+magId);
		}else{
			publisher.publishEvent(new PersonalDataEvent("GET", editionService.getEditions(magId)));			
			publisher.publishEvent(new PersonalDataEvent("MASK", editionService.getEditions(magId)));
		}
		return editions;		
	}

	@RequestMapping(API_MGZN_PATH+"/{magId}/editions/{editId}")
	public Edition getEdition(@PathVariable Long editId) {
		Edition edition = editionService.getEdition(editId);
		if(edition==null ){
			throw new ResourceNotFoundException("No Edition exists with Id:"+editId);			
		}else{
			publisher.publishEvent(new PersonalDataEvent("GET", editionService.getEdition(editId)));
			publisher.publishEvent(new PersonalDataEvent("MASK", editionService.getEdition(editId)));
		}
		return edition;
	}
	
	@RequestMapping(method=RequestMethod.POST,value=API_MGZN_PATH+"/{magId}/editions/")
	public void addEdition (@RequestBody Edition edition,  @PathVariable Long magId) {
		Magazine magazine = new Magazine();
		magazine.setMagazineID(magId);
		edition.setMagazine(magazine);		
		editionService.addEdition(edition);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value=API_MGZN_PATH+"/{magId}/editions/{editId}")
	public void updateEdition (@RequestBody Edition edition, @PathVariable Long magId, @PathVariable Long editId) {
		throwIfNonexisting(editId);
		Magazine magazine = new Magazine();
		magazine.setMagazineID(magId);
		edition.setMagazine(magazine);

		publisher.publishEvent(new PersonalDataEvent("PUT_M", edition, editionService.getEdition(editId)));
		editionService.updateEdition(editId, edition);
		publisher.publishEvent(new PersonalDataEvent("PUT_S", edition, editionService.getEdition(editId)));
	}

	@RequestMapping(method=RequestMethod.DELETE,value=API_MGZN_PATH+"/{magId}/editions/{editId}")
	public void deleteEdition (@PathVariable Long magId, @PathVariable Long editId) {
		throwIfNonexisting(editId);
		editionService.deleteEdition(editId);		
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value=API_MGZN_PATH+"/{magId}/editions")
	public void deleteAllEditions (@PathVariable Long magId) {
//		throwIfNonexisting(magId);
		editionService.deleteAllEditionByMagazineID(magId);		
	}
	
        
    private void throwIfNonexisting(long id) {
	    if (!editionService.exists(id)) {
	        NotFoundException notFoundException = new NotFoundException("Edition not found Id :"+id);
	        logger.warn("request failed", notFoundException);
	        throw notFoundException;
	    }
	}
}