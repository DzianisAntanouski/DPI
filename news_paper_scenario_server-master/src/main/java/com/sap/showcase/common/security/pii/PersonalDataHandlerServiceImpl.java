package com.sap.showcase.common.security.pii;

import java.lang.annotation.Annotation;

import com.sap.showcase.common.security.auditlog.AuditLoggingService;
import com.sap.showcase.common.security.masking.MaskingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataHandlerServiceImpl implements PersonalDataHandlerService {

	private AuditLoggingService auditLoggingService;

	private MaskingService maskService;

	@Autowired
    public void setAuditLoggingService(AuditLoggingService auditLoggingService) {
        this.auditLoggingService = auditLoggingService;
    }

    @Autowired
    public void setMaskService(MaskingService maskService) {
        this.maskService = maskService;
    }

    @EventListener(condition = "#myPersonalDataEvent.operation == 'GET'")
    public void handleContextGetEvent (PersonalDataEvent myPersonalDataEvent) {
		if (getAnnotation(myPersonalDataEvent.getListObjects().get(0))){
			auditLoggingService.logDataAccess(myPersonalDataEvent.getListObjects());
		}	
    }
	
	@EventListener(condition = "#myPersonalDataEvent.operation == 'PUT_M'")
    public void handleContextPutModEvent (PersonalDataEvent myPersonalDataEvent) {
		if (getAnnotation(myPersonalDataEvent.getCurObject())){
			auditLoggingService.logDataModificationPrepare(myPersonalDataEvent.getNewObject(), myPersonalDataEvent.getCurObject());
		}	
    }
	
	@EventListener(condition = "#myPersonalDataEvent.operation == 'PUT_S'")
    public void handleContextPutStsEvent (PersonalDataEvent myPersonalDataEvent) {
		if (getAnnotation(myPersonalDataEvent.getCurObject())){
			if(myPersonalDataEvent.getNewObject().equals(myPersonalDataEvent.getCurObject())){ 
				auditLoggingService.logDataModificationStatus(myPersonalDataEvent.getNewObject(), true);
			}	
			else{
				auditLoggingService.logDataModificationStatus(myPersonalDataEvent.getNewObject(), false);
			}
		}	
    }
	@SuppressWarnings("static-access")
	@EventListener(condition = "#myPersonalDataEvent.operation == 'MASK'")
    public void handleContextMaskEvent (PersonalDataEvent myPersonalDataEvent) {
		if (getAnnotation(myPersonalDataEvent.getListObjects().get(0))){
			Annotation annotation = myPersonalDataEvent.getListObjects().get(0).getClass().getAnnotation(PersonalData.class);
    		PersonalData persData = (PersonalData)annotation;
    		if( persData.MaskMode()==persData.MaskMode().On){
    			maskService.maskDataAccess(myPersonalDataEvent.getListObjects());
    		}			
		}	
    }
	
	private boolean getAnnotation(Object object){
		if (object!=null && object.getClass().isAnnotationPresent(PersonalData.class)){
//			System.out.printf("%nObject: %s%n", object.toString());	    		
    		Annotation annotation = object.getClass().getAnnotation(PersonalData.class);
    		PersonalData persData = (PersonalData)annotation;
//    		System.out.printf("PIType: %s%n", persData.PIType());
//    		System.out.printf("LogType: %s%n", persData.LogType());
//    		System.out.printf("Masking: %s%n", persData.MaskMode());
    		return true;
    	}
		return false;
	}
}
