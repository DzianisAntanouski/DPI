package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EOBResponse {

    private Boolean dataSubjectExpired;
    private String dataSubjectNotExpiredReason;

    public EOBResponse() {
    }

    public EOBResponse(Boolean dataSubjectExpired, String dataSubjectNotExpiredReason) {
        this.dataSubjectExpired = dataSubjectExpired;
        this.dataSubjectNotExpiredReason = dataSubjectNotExpiredReason;
    }

    public Boolean getDataSubjectExpired() {
        return dataSubjectExpired;
    }

    public void setDataSubjectExpired(Boolean dataSubjectExpired) {
        this.dataSubjectExpired = dataSubjectExpired;
    }

    public String getDataSubjectNotExpiredReason() {
        return dataSubjectNotExpiredReason;
    }

    public void setDataSubjectNotExpiredReason(String dataSubjectNotExpiredReason) {
        this.dataSubjectNotExpiredReason = dataSubjectNotExpiredReason;
    }

    @Override
    public String toString() {
        return "EOBResponse{" +
                "dataSubjectExpired=" + dataSubjectExpired +
                ", dataSubjectNotExpiredReason='" + dataSubjectNotExpiredReason + '\'' +
                '}';
    }
}
