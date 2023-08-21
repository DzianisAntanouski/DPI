/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResidenceRule {

    private String residenceDate;
    private List<ConditionSet> conditionSet;

    public ResidenceRule() {
    }

    public ResidenceRule(String residenceDate, List<ConditionSet> conditionSet) {
        this.residenceDate = residenceDate;
        this.conditionSet = conditionSet;
    }

    public String getResidenceDate() {
        return residenceDate;
    }

    public void setResidenceDate(String residenceDate) {
        this.residenceDate = residenceDate;
    }

    public List<ConditionSet> getConditionSet() {
        return conditionSet;
    }

    public void setConditionSet(List<ConditionSet> conditionSet) {
        this.conditionSet = conditionSet;
    }

    @Override
    public String toString() {
        return "ResidenceRule{" +
                "residenceDate='" + residenceDate + '\'' +
                ", conditionSet=" + conditionSet +
                '}';
    }
}
