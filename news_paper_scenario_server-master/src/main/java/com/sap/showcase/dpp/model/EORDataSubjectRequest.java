/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EORDataSubjectRequest {

    private String legalGround;
    private String dataSubjectRole;
    private String startTime;
    private List<LegalEntityResidenceRule> legalEntitiesResidenceRules;

    public EORDataSubjectRequest() {
    }

    public EORDataSubjectRequest(String legalGround, String dataSubjectRole, String startTime, List<LegalEntityResidenceRule> legalEntitiesResidenceRules) {
        this.legalGround = legalGround;
        this.dataSubjectRole = dataSubjectRole;
        this.startTime = startTime;
        this.legalEntitiesResidenceRules = legalEntitiesResidenceRules;
    }

    public String getLegalGround() {
        return legalGround;
    }

    public void setLegalGround(String legalGround) {
        this.legalGround = legalGround;
    }

    public String getDataSubjectRole() {
        return dataSubjectRole;
    }

    public void setDataSubjectRole(String dataSubjectRole) {
        this.dataSubjectRole = dataSubjectRole;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<LegalEntityResidenceRule> getLegalEntitiesResidenceRules() {
        return legalEntitiesResidenceRules;
    }

    public void setLegalEntitiesResidenceRules(List<LegalEntityResidenceRule> legalEntitiesResidenceRules) {
        this.legalEntitiesResidenceRules = legalEntitiesResidenceRules;
    }

    @Override
    public String toString() {
        return "EORDataSubjectRequest{" +
                "legalGround='" + legalGround + '\'' +
                ", dataSubjectRole='" + dataSubjectRole + '\'' +
                ", startTime='" + startTime + '\'' +
                ", legalEntitiesResidenceRules=" + legalEntitiesResidenceRules +
                '}';
    }
}
