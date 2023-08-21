/*
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 *
 */

package com.sap.showcase.dpp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LegalEntity {
    private String value;
    private String valueDesc;
    private String legalEntity;
    
    public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getValueDesc() {
        return valueDesc;
    }
    public void setValueDesc(String valueDesc) {
        this.valueDesc = valueDesc;
    }
    @Override
    public String toString() {
        return "LegalEntity{" +
                "legalEntity='" + legalEntity + '\'' +
                '}';
    }
}
