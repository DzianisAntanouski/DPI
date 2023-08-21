/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConditionSet {

    private String conditionFieldName;
    private String conditionFieldValue;

    public ConditionSet() {
    }

    public ConditionSet(String conditionFieldName) {
        this.conditionFieldName = conditionFieldName;
    }

    public String getConditionFieldName() {
        return conditionFieldName;
    }

    public void setConditionFieldName(String conditionFieldName) {
        this.conditionFieldName = conditionFieldName;
    }

    public String getConditionFieldValue() {
        return conditionFieldValue;
    }

    public void setConditionFieldValue(String conditionFieldValue) {
        this.conditionFieldValue = conditionFieldValue;
    }

    @Override
    public String toString() {
        return "ConditionSet{" +
                "conditionFieldName='" + conditionFieldName + '\'' +
                ", conditionFieldValue='" + conditionFieldValue + '\'' +
                '}';
    }
}
