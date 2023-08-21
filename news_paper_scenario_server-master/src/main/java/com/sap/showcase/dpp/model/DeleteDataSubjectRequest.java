package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteDataSubjectRequest {
    private String applicationGroupName;
    private String dataSubjectRole;
    private String dataSubjectID;
    private String maxDeletionDate;

    public DeleteDataSubjectRequest() {
    }

    public DeleteDataSubjectRequest(String applicationGroupName, String dataSubjectRole, String dataSubjectID, String maxDeletionDate) {
        this.applicationGroupName = applicationGroupName;
        this.dataSubjectRole = dataSubjectRole;
        this.dataSubjectID = dataSubjectID;
        this.maxDeletionDate = maxDeletionDate;
    }

    public String getApplicationGroupName() {
        return applicationGroupName;
    }

    public void setApplicationGroupName(String applicationGroupName) {
        this.applicationGroupName = applicationGroupName;
    }

    public String getDataSubjectRole() {
        return dataSubjectRole;
    }

    public void setDataSubjectRole(String dataSubjectRole) {
        this.dataSubjectRole = dataSubjectRole;
    }

    public String getDataSubjectID() {
        return dataSubjectID;
    }

    public void setDataSubjectID(String dataSubjectID) {
        this.dataSubjectID = dataSubjectID;
    }

    public String getMaxDeletionDate() {
        return maxDeletionDate;
    }

    public void setMaxDeletionDate(String maxDeletionDate) {
        this.maxDeletionDate = maxDeletionDate;
    }

    @Override
    public String toString() {
        return "DeleteDataSubjectRequest{" +
                "applicationGroupName='" + applicationGroupName + '\'' +
                ", dataSubjectRole='" + dataSubjectRole + '\'' +
                ", dataSubjectID='" + dataSubjectID + '\'' +
                ", maxDeletionDate='" + maxDeletionDate + '\'' +
                '}';
    }
}
