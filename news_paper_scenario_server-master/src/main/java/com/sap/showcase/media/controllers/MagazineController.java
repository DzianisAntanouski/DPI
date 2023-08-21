package com.sap.showcase.media.controllers;

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
import com.sap.showcase.media.model.Magazine;
import com.sap.showcase.media.service.MagazineService;

import static com.sap.showcase.media.controllers.ControllerConst.API_MGZN_PATH;

import java.util.List;

@RestController
public class MagazineController {

	@Autowired
	private MagazineService magazineService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("description");
    }

	@RequestMapping(API_MGZN_PATH)
	public Object getAllMagazines() {
		List<Magazine> magazines = magazineService.getMagazines();
		if (magazines.size()==0){
			throw new ResourceNotFoundException("No Magazines found");
		}else{
			publisher.publishEvent(new PersonalDataEvent("GET", magazineService.getMagazines()));		
			publisher.publishEvent(new PersonalDataEvent("MASK", magazineService.getMagazines()));
		}	
		return magazines;
	}
	
	@RequestMapping(API_MGZN_PATH+"/{id}")	
	public Magazine getMagazine(@PathVariable Long id) {
		Magazine magazine= magazineService.getMagazine(id);
		if (magazine == null || magazine.getMagazineID()==0){
			throw new ResourceNotFoundException("No Magazine exists with Id:"+id);
		}else{
			publisher.publishEvent(new PersonalDataEvent("GET", magazineService.getMagazine(id)));
			publisher.publishEvent(new PersonalDataEvent("MASK", magazineService.getMagazine(id)));
		}
		return magazine;
	}
	
	@RequestMapping(method=RequestMethod.POST,value=API_MGZN_PATH)
	public void addMagazine (@RequestBody Magazine magazine) {
		magazineService.addMagazine(magazine);		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value=API_MGZN_PATH)	
	public void updateMagazine (@RequestBody Magazine magazine) {
		
		throwIfNonexisting(magazine.getMagazineID());
		publisher.publishEvent(new PersonalDataEvent("PUT_M", magazineService.getMagazine(magazine.getMagazineID()), magazine));
		magazineService.updateMagazine(magazine.getMagazineID(),magazine);
		publisher.publishEvent(new PersonalDataEvent("PUT_S", magazineService.getMagazine(magazine.getMagazineID()), magazine));		
	}

	@RequestMapping(method=RequestMethod.DELETE,value=API_MGZN_PATH+"/{id}")
	public void deleteMagazine (@PathVariable Long id) {
		throwIfNonexisting(id);
		magazineService.deleteMagazine(id);		
	}	
	@RequestMapping(method=RequestMethod.DELETE,value=API_MGZN_PATH)
	public void deleteAllMagazine () {
		 magazineService.deleteAllMagazines();		
	}	
	private void throwIfNonexisting(long id) {
	    if (!magazineService.exists(id)) {
	        NotFoundException notFoundException = new NotFoundException("Magazine not found id:"+id);
	        LoggerFactory.getLogger(this.getClass()).warn("request failed", notFoundException);
	        throw notFoundException;
	    }
	}
}