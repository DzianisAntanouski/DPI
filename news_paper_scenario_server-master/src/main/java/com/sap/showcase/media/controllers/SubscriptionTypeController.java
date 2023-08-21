package com.sap.showcase.media.controllers;
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
import com.sap.showcase.media.model.SubscriptionType;
import com.sap.showcase.media.service.SubscriptionTypeService;

import static com.sap.showcase.media.controllers.ControllerConst.API_SUBT_PATH;

@RestController
public class SubscriptionTypeController {

	@Autowired
	private SubscriptionTypeService subscriptionTypeService;
	private ResourceNotFoundException noSubscriptionType = new ResourceNotFoundException("SubscriptionType not found");

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("subscriptionTypeId","description","price","oneTime");
    }
		
	@RequestMapping(API_SUBT_PATH)
	public Object getAllSubscriptionTypes() {		
		List<SubscriptionType> subscriptionTypes = subscriptionTypeService.getSubscriptionTypes();
		if (subscriptionTypes.size()==0)
			throw noSubscriptionType;
		return subscriptionTypes;		
	
	}
	@RequestMapping(API_SUBT_PATH+"/{id}")
	public SubscriptionType getSubscriptionType(@PathVariable Long id) {
		SubscriptionType subscriptionType= subscriptionTypeService.getsubscriptionType(id);
		if (subscriptionType==null )
			throw noSubscriptionType;
		return subscriptionType;

	}
	@RequestMapping(method=RequestMethod.POST,value=API_SUBT_PATH)
	public void addSubscriptionType (@RequestBody SubscriptionType subscriptionType) {
		subscriptionTypeService.addSubscriptionType(subscriptionType);
		
	}
	

	@RequestMapping(method=RequestMethod.PUT,value=API_SUBT_PATH+"/{id}")
	public void updateSubscriptionType (@RequestBody SubscriptionType subscriptionType ) {
		if (subscriptionTypeService.getsubscriptionType(subscriptionType.getSubscriptionTypeId())==null){
			throw new ResourceNotFoundException("Cannot Update as no such subscriptionType exists:"+subscriptionType.getSubscriptionTypeId());
		}
		subscriptionTypeService.updateSubscriptionType(subscriptionType.getSubscriptionTypeId(), subscriptionType);
		
	}


	@RequestMapping(method=RequestMethod.DELETE,value=API_SUBT_PATH+"/{id}")
	public void deleteSubscriptionType (@PathVariable Long id) {
		subscriptionTypeService.deleteSubscriptionType(id);
		
	}
	@RequestMapping(method=RequestMethod.DELETE,value=API_SUBT_PATH)
	public void deleteAllSubscriptionTypes() {
		subscriptionTypeService.deleteAllSubscriptionTypes();
		
	}

}
