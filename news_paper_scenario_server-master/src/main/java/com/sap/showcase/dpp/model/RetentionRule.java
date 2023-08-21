/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetentionRule {

    private String legalEntity;
    private Integer retentionPeriod;
    private String retentionUnit;
    private List<ConditionSet> rulesConditionSet;

    public RetentionRule() {
    }

    public RetentionRule(String legalEntity, Integer retentionPeriod, String retentionUnit, List<ConditionSet> rulesConditionSet) {
        this.legalEntity = legalEntity;
        this.retentionPeriod = retentionPeriod;
        this.retentionUnit = retentionUnit;
        this.rulesConditionSet = rulesConditionSet;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public Integer getRetentionPeriod() {
        return retentionPeriod;
    }

    public void setRetentionPeriod(Integer retentionPeriod) {
        this.retentionPeriod = retentionPeriod;
    }

    public String getRetentionUnit() {
        return retentionUnit;
    }

    public void setRetentionUnit(String retentionUnit) {
        this.retentionUnit = retentionUnit;
    }

    public List<ConditionSet> getRulesConditionSet() {
        return rulesConditionSet;
    }

    public void setRulesConditionSet(List<ConditionSet> rulesConditionSet) {
        this.rulesConditionSet = rulesConditionSet;
    }

    @Override
    public String toString() {
        return "RetentionRule{" +
                "legalEntity='" + legalEntity + '\'' +
                ", retentionPeriod=" + retentionPeriod +
                ", retentionUnit='" + retentionUnit + '\'' +
                ", rulesConditionSet=" + rulesConditionSet +
                '}';
    }
}
