package com.sap.showcase.common.security.auditlog;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.xs.audit.api.AuditLogMessageFactory;
import com.sap.xs.audit.api.exception.AuditLogException;
import org.springframework.beans.factory.annotation.Autowired;

public class AuditLoggingServiceImpl implements AuditLoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLoggingServiceImpl.class);

	private AuditLogMessageFactory auditLogMessageFactory;

    private AuditLogContext auditLogContext;

    @Autowired
    public void setAuditLogContext(AuditLogContext auditLogContext) {
        this.auditLogContext = auditLogContext;
    }

    public AuditLoggingServiceImpl(final AuditLogMessageFactory auditLogMessageFactory){
        this.auditLogMessageFactory = auditLogMessageFactory;
    }
	
	public void logDataAccess(List<?> listObj ) {
       try {
    	    if (listObj.size() > 1){
                auditLogContext.setObjectID("Mass Mode for " + listObj.size() + " records");
    	    }
    	    else{
                auditLogContext.setObjectID(listObj.get(0).toString());
    	    }

    	    auditLogContext.logDataAccess(auditLogMessageFactory);
            LOGGER.info("Success! This was a DPP relevant data access to object");
        }                
        catch (AuditLogException e) {
            LOGGER.error("Fail to write Audit Log!: {}", e.toString());
        }            
    }
	
    public void logDataModificationPrepare(Object newObj, Object oldObj  ) {
       try {
            auditLogContext.setObjectID(newObj.toString());
            auditLogContext.setObjectValueOld(oldObj.toString());
            auditLogContext.setObjectValueNew(newObj.toString());
            auditLogContext.logDataModificationPrepare(auditLogMessageFactory);
            LOGGER.info("Success! This was a DPP relevant data object modification");
        } 
       catch (AuditLogException e) {        
            LOGGER.error("Fail to write Audit Log!: {}", e.toString());
       }
        
    }
	public void logDataModificationStatus(Object obj, boolean status  ) {
		try {
            auditLogContext.setObjectID(obj.toString());
            auditLogContext.logDataModificationStatus(auditLogMessageFactory, status );
		    LOGGER.info("Success! This was a DPP relevant data object modification status");
		} 
		catch (AuditLogException e) {        
	      LOGGER.error("Fail to write Audit Log!: {}", e.toString());
		}      
    }
}

