package com.sap.showcase.common.security.auditlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuditLoggingServiceLocalImpl implements AuditLoggingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLoggingServiceLocalImpl.class);

    @Override
    public void logDataAccess(List<?> listObj) {
        // TODO whatever makes sense
        //LOGGER.info(...);
    }

    @Override
    public void logDataModificationPrepare(Object newObj, Object oldObj) {
        // TODO whatever makes sense
        //LOGGER.info(...);
    }

    @Override
    public void logDataModificationStatus(Object obj, boolean status) {
        // TODO whatever makes sense
        //LOGGER.info(...);
    }
}
