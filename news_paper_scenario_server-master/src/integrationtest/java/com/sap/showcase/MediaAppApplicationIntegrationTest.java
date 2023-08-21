package com.sap.showcase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

public class MediaAppApplicationIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ApplicationContext applicationContext;

	@Test
	public void testApplicationContextLoaded() {
        Assert.assertNotNull(applicationContext);
	}

    @Test
    public void testAuditLoggingServiceLoaded() {
        Assert.assertNotNull(applicationContext.getBean("auditLoggingService"));
    }
}