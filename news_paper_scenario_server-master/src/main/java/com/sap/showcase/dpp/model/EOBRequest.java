package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EOBRequest {

    private String legalGround;
    private String dataSubjectID;
    private String dataSubjectRole;

    public EOBRequest() {
    }

    public EOBRequest(String legalGround, String dataSubjectID, String dataSubjectRole) {
        this.legalGround = legalGround;
        this.dataSubjectID = dataSubjectID;
        this.dataSubjectRole = dataSubjectRole;
    }

    public String getLegalGround() {
        return legalGround;
    }

    public void setLegalGround(String legalGround) {
        this.legalGround = legalGround;
    }

    public String getDataSubjectID() {
        return dataSubjectID;
    }

    public void setDataSubjectID(String dataSubjectID) {
        this.dataSubjectID = dataSubjectID;
    }

    public String getDataSubjectRole() {
        return dataSubjectRole;
    }

    public void setDataSubjectRole(String dataSubjectRole) {
        this.dataSubjectRole = dataSubjectRole;
    }

    @Override
    public String toString() {
        return "EOBRequest{" +
                "legalGround='" + legalGround + '\'' +
                ", dataSubjectID='" + dataSubjectID + '\'' +
                ", dataSubjectRole='" + dataSubjectRole + '\'' +
                '}';
    }
}
