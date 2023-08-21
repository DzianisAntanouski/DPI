/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetentionStartDateRequest {

    private String legalGround;
    private String dataSubjectID;
    private String dataSubjectRole;
    private String legalEntity;
    private String startTime;
    private List<RulesConditionSet> rulesConditionSet;

    public RetentionStartDateRequest() {
    }

    public RetentionStartDateRequest(String legalGround, String dataSubjectID, String dataSubjectRole, String legalEntity, String startTime, List<RulesConditionSet> rulesConditionSet) {
        this.legalGround = legalGround;
        this.dataSubjectID = dataSubjectID;
        this.dataSubjectRole = dataSubjectRole;
        this.legalEntity = legalEntity;
        this.startTime = startTime;
        this.rulesConditionSet = rulesConditionSet;
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

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<RulesConditionSet> getRulesConditionSet() {
        return rulesConditionSet;
    }

    public void setRulesConditionSet(List<RulesConditionSet> rulesConditionSet) {
        this.rulesConditionSet = rulesConditionSet;
    }

    @Override
    public String toString() {
        return "RetentionStartDateRequest{" +
                "legalGround='" + legalGround + '\'' +
                ", dataSubjectID='" + dataSubjectID + '\'' +
                ", dataSubjectRole='" + dataSubjectRole + '\'' +
                ", legalEntity='" + legalEntity + '\'' +
                ", startTime='" + startTime + '\'' +
                ", rulesConditionSet=" + rulesConditionSet +
                '}';
    }
}
