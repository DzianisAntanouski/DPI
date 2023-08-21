/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 *
 */

package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LegalEntitiesRequest {

    private String legalGround;
    private String dataSubjectID;
    private String dataSubjectRole;

    public LegalEntitiesRequest() {
    }

    public LegalEntitiesRequest(String legalGround, String dataSubjectID, String dataSubjectRole) {
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
        return "LegalEntitiesRequest{" +
                "legalGround='" + legalGround + '\'' +
                ", dataSubjectID='" + dataSubjectID + '\'' +
                ", dataSubjectRole='" + dataSubjectRole + '\'' +
                '}';
    }
}
