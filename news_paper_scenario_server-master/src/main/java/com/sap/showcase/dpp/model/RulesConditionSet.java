/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RulesConditionSet {

    private String retentionID;
    private List<ConditionSet> conditionSet;

    public RulesConditionSet() {
    }

    public RulesConditionSet(String retentionID, List<ConditionSet> conditionSet) {
        this.retentionID = retentionID;
        this.conditionSet = conditionSet;
    }

    public String getRetentionID() {
        return retentionID;
    }

    public void setRetentionID(String retentionID) {
        this.retentionID = retentionID;
    }

    public List<ConditionSet> getConditionSet() {
        return conditionSet;
    }

    public void setConditionSet(List<ConditionSet> conditionSet) {
        this.conditionSet = conditionSet;
    }

    @Override
    public String toString() {
        return "RulesConditionSet{" +
                "retentionID='" + retentionID + '\'' +
                ", conditionSet=" + conditionSet +
                '}';
    }
}
