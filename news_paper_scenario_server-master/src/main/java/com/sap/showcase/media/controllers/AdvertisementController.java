package com.sap.showcase.media.controllers;

import static com.sap.showcase.media.controllers.ControllerConst.API_MGZN_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sap.showcase.exception.ResourceNotFoundException;
import com.sap.showcase.media.model.Advertisement;
import com.sap.showcase.media.model.AdvertisementType;
import com.sap.showcase.media.model.Magazine;
import com.sap.showcase.media.service.AdvertisementService;

@RestController
public class AdvertisementController {	

	@Autowired
	private AdvertisementService advertisementService;

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("advertisement_ID","description","author","advertisementType","magazine");
    }
	
	@RequestMapping(API_MGZN_PATH+"/{magId}/advertisements")
	public Object getAllAdvertisementsByMagazineID(@PathVariable Long magId) {		
		List<Advertisement> advertisements = advertisementService.getAdvertisementsByMagazineID(magId);
		if(advertisements.size()==0){
			throw new ResourceNotFoundException("No Advertisements found");
		}
		return advertisementService.getAdvertisementsByMagazineID(magId);		
	}
	
	@RequestMapping(API_MGZN_PATH+"/{magId}/advertisements/{id}")
	public Advertisement getAdvertisement(@PathVariable Long id) {		
		Advertisement advertisement = advertisementService.getAdvertisement(id);
		if (advertisement==null ){
			throw new ResourceNotFoundException("No Advertisement exists with Id:"+id);
		}
		return advertisement;
	}
	@RequestMapping(method=RequestMethod.POST,value=API_MGZN_PATH+"/{magId}/advertisements/{advertisementTypeId}")
	public void addAdvertisement (@RequestBody Advertisement advertisement, 
									@PathVariable Long advertisementTypeId, @PathVariable Long magId) {
		Magazine magazine = new Magazine();
		magazine.setMagazineID(magId);
		advertisement.setMagazine(magazine);
		
		AdvertisementType advertisementType = new AdvertisementType();
		advertisementType.setAdvertisementTypeId(advertisementTypeId);
		advertisement.setAdvertisementType(advertisementType);
		advertisementService.addAdvertisement(advertisement);		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value=API_MGZN_PATH+"/{magId}/advertisements/{advertisementId}/{advertisementTypeId}")
	public void updateAdvertisement (@RequestBody Advertisement advertisement, @PathVariable Long advertisementId,
										@PathVariable Long advertisementTypeId, @PathVariable Long magId) {
		Magazine magazine = new Magazine();
		magazine.setMagazineID(magId);
		advertisement.setMagazine(magazine);
		
		advertisement.setAdvertisement_ID(advertisementId);
		AdvertisementType advertisementType = new AdvertisementType();
		advertisementType.setAdvertisementTypeId(advertisementTypeId);
		advertisement.setAdvertisementType(advertisementType);
		advertisementService.updateAdvertisement(advertisement);
		
	}

	@RequestMapping(method=RequestMethod.DELETE,value=API_MGZN_PATH+"/{magId}/advertisements/{id}")
	public void deleteAdvertisement (@PathVariable Long id) {
		 advertisementService.deleteAdvertisement(id);		
	}
}