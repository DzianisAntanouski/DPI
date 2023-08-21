package com.sap.showcase;

import com.sap.showcase.common.security.auditlog.AuditLogContext;
import com.sap.showcase.common.security.auditlog.AuditLoggingService;
import com.sap.showcase.common.security.auditlog.AuditLoggingServiceImpl;
import com.sap.showcase.common.security.auditlog.AuditLoggingServiceLocalImpl;
import com.sap.showcase.common.security.pii.PersonalDataHandlerService;
import com.sap.showcase.common.security.pii.PersonalDataHandlerServiceImpl;
import com.sap.xs.audit.api.exception.AuditLogException;
import com.sap.xs.audit.client.impl.AuditLogMessageFactoryImpl;
import com.sap.xs.env.VcapServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class MediaAppApplicationConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaAppApplication.class);

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    AuditLogContext auditLogHelper() {
        final AuditLogContext helper = new AuditLogContext();
        helper.initializeUserContext();
        return helper;
    }

    @Bean
    AuditLoggingService auditLoggingService() {
        try {
            final AuditLogMessageFactoryImpl auditLogMessageFactory = new AuditLogMessageFactoryImpl(new VcapServices());
            return new AuditLoggingServiceImpl(auditLogMessageFactory);
        }
        catch(NoClassDefFoundError|AuditLogException e) {
            LOGGER.warn("Fail to initiate  AuditLog Service!", e);
            return new AuditLoggingServiceLocalImpl();
        }
    }

    @Bean
    PersonalDataHandlerService personalDataHandler() {
        return new PersonalDataHandlerServiceImpl();
    }
}
