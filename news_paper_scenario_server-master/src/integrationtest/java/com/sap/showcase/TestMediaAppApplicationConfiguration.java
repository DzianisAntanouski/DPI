package com.sap.showcase;

import com.sap.showcase.common.security.auditlog.AuditLogHelper;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class TestMediaAppApplicationConfiguration {

    @Bean
    @Primary
    AuditLogHelper auditLogHelper() {
        return Mockito.mock(AuditLogHelper.class);
    }
}
