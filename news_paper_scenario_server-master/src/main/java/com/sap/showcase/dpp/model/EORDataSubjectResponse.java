/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EORDataSubjectResponse {

    List<DataSubjectId> success = new ArrayList<>();
    List<DataSubjectId> nonConfirmCondition = new ArrayList<>();

    public EORDataSubjectResponse() {
    }

    public EORDataSubjectResponse(List<DataSubjectId> success) {
        this.success = success;
    }

    public List<DataSubjectId> getSuccess() {
        return success;
    }

    public void setSuccess(List<DataSubjectId> success) {
        this.success = success;
    }

    public List<DataSubjectId> getNonConfirmCondition() {
        return nonConfirmCondition;
    }

    public void setNonConfirmCondition(List<DataSubjectId> nonConfirmCondition) {
        this.nonConfirmCondition = nonConfirmCondition;
    }

    @Override
    public String toString() {
        return "EORDataSubjectResponse{" +
                "success=" + success +
                ", nonConfirmCondition=" + nonConfirmCondition +
                '}';
    }
}
