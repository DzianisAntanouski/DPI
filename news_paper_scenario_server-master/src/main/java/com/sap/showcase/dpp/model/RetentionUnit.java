package com.sap.showcase.dpp.model;

import java.time.temporal.TemporalUnit;

import static java.time.temporal.ChronoUnit.*;

public enum RetentionUnit {

    DAY(DAYS), MON(MONTHS), ANN(YEARS);

    private final TemporalUnit temporalUnit;

    RetentionUnit(TemporalUnit temporalUnit) {
        this.temporalUnit = temporalUnit;
    }

    public TemporalUnit getTemporalUnit() {
        return temporalUnit;
    }
}
