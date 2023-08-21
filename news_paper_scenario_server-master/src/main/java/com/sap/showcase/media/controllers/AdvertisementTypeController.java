package com.sap.showcase.media.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sap.showcase.exception.NotFoundException;
import com.sap.showcase.exception.ResourceNotFoundException;
import com.sap.showcase.media.model.AdvertisementType;
import com.sap.showcase.media.service.AdvertisementTypeService;

import static com.sap.showcase.media.controllers.ControllerConst.API_ADVT_PATH;

@RestController
public class AdvertisementTypeController {
	@Autowired
	private AdvertisementTypeService advertisementTypeService;

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("advertisementTypeId","description","price");
    }

	@RequestMapping(API_ADVT_PATH)
	public Object getAllAdvertisementTypes() {
		List<AdvertisementType> advertisementTypes = advertisementTypeService.getAdvertisementTypes();
		if (advertisementTypes.size()==0){
			throw new ResourceNotFoundException("No Advertisement Types found");
		}
		return advertisementTypes;
		
	}
	@RequestMapping(API_ADVT_PATH+"/{id}")
	public AdvertisementType getAdvertisementType(@PathVariable Long id) {
		AdvertisementType advertisementType = advertisementTypeService.getadvertisementType(id);
		if (advertisementType==null){
			throw new ResourceNotFoundException("No Advertisement Type exists with Id:"+id);
		}
		return advertisementType;
	}
	@RequestMapping(method=RequestMethod.POST,value=API_ADVT_PATH)
	public void addAdvertisementType (@RequestBody AdvertisementType advertisementType) {
		advertisementTypeService.addAdvertisementType(advertisementType);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value=API_ADVT_PATH+"/{id}")
	public void updateAdvertisementType (@RequestBody AdvertisementType advertisementType) {

		throwIfNonexisting(advertisementType.getAdvertisementTypeId());
		advertisementTypeService.updateAdvertisementType(advertisementType.getAdvertisementTypeId(),advertisementType);
		
	}

	@RequestMapping(method=RequestMethod.DELETE,value=API_ADVT_PATH+"/{id}")
	public void deleteAdvertisementType (@PathVariable Long id) {
		advertisementTypeService.deleteAdvertisementType(id);
		
	}
	@RequestMapping(method=RequestMethod.DELETE,value=API_ADVT_PATH)
	public void deleteAllAdvertisementTypes() {
		advertisementTypeService.deleteAllAdvertisementTypes();
		
	}
	private void throwIfNonexisting(long id) {
	    if (!advertisementTypeService.exists(id)) {
	        NotFoundException notFoundException = new NotFoundException("AdvertisemnentType not found id:"+id);
	        LoggerFactory.getLogger(this.getClass()).warn("request failed", notFoundException);
	        throw notFoundException;
	    }
	}

}