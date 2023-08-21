/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LegalGroundDeletionRequest {

    private String legalGround;
    private String dataSubjectID;
    private String dataSubjectRole;
    private String startTime;
    private String maxDeletionDate;
    private List<RetentionRule> retentionRules;

    public LegalGroundDeletionRequest() {
    }

    public LegalGroundDeletionRequest(String legalGround, String dataSubjectID, String dataSubjectRole, String startTime, String maxDeletionDate, List<RetentionRule> retentionRules) {
        this.legalGround = legalGround;
        this.dataSubjectID = dataSubjectID;
        this.dataSubjectRole = dataSubjectRole;
        this.startTime = startTime;
        this.maxDeletionDate = maxDeletionDate;
        this.retentionRules = retentionRules;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMaxDeletionDate() {
        return maxDeletionDate;
    }

    public void setMaxDeletionDate(String maxDeletionDate) {
        this.maxDeletionDate = maxDeletionDate;
    }

    public List<RetentionRule> getRetentionRules() {
        return retentionRules;
    }

    public void setRetentionRules(List<RetentionRule> retentionRules) {
        this.retentionRules = retentionRules;
    }

    @Override
    public String toString() {
        return "LegalGroundDeletionRequest{" +
                "legalGround='" + legalGround + '\'' +
                ", dataSubjectID='" + dataSubjectID + '\'' +
                ", dataSubjectRole='" + dataSubjectRole + '\'' +
                ", startTime='" + startTime + '\'' +
                ", maxDeletionDate='" + maxDeletionDate + '\'' +
                ", retentionRules=" + retentionRules +
                '}';
    }
}
