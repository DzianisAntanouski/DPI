/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LegalEntityResidenceRule {

    private String legalEntity;
    private List<ResidenceRule> residenceRules;

    public LegalEntityResidenceRule() {
    }

    public LegalEntityResidenceRule(String legalEntity, List<ResidenceRule> residenceRules) {
        this.legalEntity = legalEntity;
        this.residenceRules = residenceRules;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public List<ResidenceRule> getResidenceRules() {
        return residenceRules;
    }

    public void setResidenceRules(List<ResidenceRule> residenceRules) {
        this.residenceRules = residenceRules;
    }

    @Override
    public String toString() {
        return "LegalEntityResidenceRule{" +
                "legalEntity='" + legalEntity + '\'' +
                ", residenceRules=" + residenceRules +
                '}';
    }
}
