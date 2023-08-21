/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EORDataSubjectConfirmationRequest {

    private String legalGround;
    private String dataSubjectRole;
    private String startTime;
    private List<LegalEntityResidenceRule> legalEntitiesResidenceRules;
    private Set<DataSubjectId> dataSubjects;

    public EORDataSubjectConfirmationRequest() {
    }

    public EORDataSubjectConfirmationRequest(String legalGround, String dataSubjectRole, String startTime, List<LegalEntityResidenceRule> legalEntitiesResidenceRules, Set<DataSubjectId> dataSubjects) {
        this.legalGround = legalGround;
        this.dataSubjectRole = dataSubjectRole;
        this.startTime = startTime;
        this.legalEntitiesResidenceRules = legalEntitiesResidenceRules;
        this.dataSubjects = dataSubjects;
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

    public Set<DataSubjectId> getDataSubjects() {
        return dataSubjects;
    }

    public void setDataSubjects(Set<DataSubjectId> dataSubjects) {
        this.dataSubjects = dataSubjects;
    }

    @Override
    public String toString() {
        return "EORDataSubjectConfirmationRequest{" +
                "legalGround='" + legalGround + '\'' +
                ", dataSubjectRole='" + dataSubjectRole + '\'' +
                ", startTime='" + startTime + '\'' +
                ", legalEntitiesResidenceRules=" + legalEntitiesResidenceRules +
                ", dataSubjects=" + dataSubjects +
                '}';
    }
}
