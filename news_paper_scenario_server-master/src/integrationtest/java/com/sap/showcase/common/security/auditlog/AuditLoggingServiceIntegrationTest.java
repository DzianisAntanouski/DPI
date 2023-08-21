package com.sap.showcase.common.security.auditlog;

import static org.mockito.Mockito.*;

import com.sap.showcase.AbstractIntegrationTest;
import com.sap.xs.audit.api.exception.AuditLogException;
import com.sap.xs.audit.client.impl.AuditLogMessageFactoryImpl;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AuditLoggingServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    AuditLoggingService auditLoggingService;

    @Autowired
    AuditLogHelper auditLogHelperMock;

    @Test
    public void testLogDataAccess() throws AuditLogException {
        final List objects = new ArrayList<>();
        objects.add(new Object(){
            @Override
            public String toString() {
                return "myTestObjectInstance";
            }
        });
        auditLoggingService.logDataAccess(objects);

        verify(auditLogHelperMock, times(1)).logDataAccess(Matchers.any(AuditLogMessageFactoryImpl.class));
    }
}