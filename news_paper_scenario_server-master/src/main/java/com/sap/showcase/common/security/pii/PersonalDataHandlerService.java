package com.sap.showcase.common.security.pii;

public interface PersonalDataHandlerService {
    void handleContextGetEvent (PersonalDataEvent myPersonalDataEvent);
    void handleContextPutModEvent (PersonalDataEvent myPersonalDataEvent);
    void handleContextPutStsEvent (PersonalDataEvent myPersonalDataEvent);
    void handleContextMaskEvent (PersonalDataEvent myPersonalDataEvent);

}
