package com.sap.showcase.common.security.auditlog;

import java.util.List;

public interface AuditLoggingService {
    void logDataAccess(List<?> listObj);

    void logDataModificationPrepare(Object newObj, Object oldObj);

    void logDataModificationStatus(Object obj, boolean status);
}
