/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSubjectId {

    private String dataSubjectID;

    public DataSubjectId() {
    }

    public DataSubjectId(String dataSubjectID) {
        this.dataSubjectID = dataSubjectID;
    }

    public String getDataSubjectID() {
        return dataSubjectID;
    }

    public void setDataSubjectID(String dataSubjectID) {
        this.dataSubjectID = dataSubjectID;
    }

    @Override
    public String toString() {
        return "DataSubjectId{" +
                "dataSubjectID='" + dataSubjectID + '\'' +
                '}';
    }
}
