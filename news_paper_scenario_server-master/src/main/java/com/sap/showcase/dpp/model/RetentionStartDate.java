/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetentionStartDate {

    private String retentionID;
    private String retentionStartDate;

    public RetentionStartDate() {
    }

    public RetentionStartDate(String retentionID, String retentionStartDate) {
        this.retentionID = retentionID;
        this.retentionStartDate = retentionStartDate;
    }

    public String getRetentionID() {
        return retentionID;
    }

    public void setRetentionID(String retentionID) {
        this.retentionID = retentionID;
    }

    public String getRetentionStartDate() {
        return retentionStartDate;
    }

    public void setRetentionStartDate(String retentionStartDate) {
        this.retentionStartDate = retentionStartDate;
    }

    @Override
    public String toString() {
        return "RetentionStartDate{" +
                "retentionID='" + retentionID + '\'' +
                ", retentionStartDate='" + retentionStartDate + '\'' +
                '}';
    }
}
