package com.sap.showcase.common.security.pii;

import static org.mockito.Mockito.*;

import com.sap.showcase.common.security.auditlog.AuditLoggingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

public class PersonalDataHandlerServiceTest {

    PersonalDataHandlerServiceImpl personalDataHandlerService;

    @Mock
    AuditLoggingService auditLoggingServiceStub;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        personalDataHandlerService = new PersonalDataHandlerServiceImpl();
        personalDataHandlerService.setAuditLoggingService(auditLoggingServiceStub);
    }

	@Test
	public void testHandleContextGetEvent() {
        final List objectList = new ArrayList<PersonalDataHolderMockObject>();
	    final PersonalDataHolderMockObject pdmo = new PersonalDataHolderMockObject();
	    pdmo.setFirstName("Fritz");
        pdmo.setLastName("Müller");
        pdmo.setUser("fmueller");
        pdmo.setGender("male");
	    objectList.add(pdmo);
        final PersonalDataEvent mockGetEvent = new PersonalDataEvent("GET", objectList);

        personalDataHandlerService.handleContextGetEvent(mockGetEvent);

        verify(auditLoggingServiceStub, times(1)).logDataAccess(objectList);
	}

	@PersonalData
	private class PersonalDataHolderMockObject {
	    private String user;
	    private String firstName;
	    private String lastName;
	    private String gender;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}