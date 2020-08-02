package com.craftsoft.callDetailRecord.details;

import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.sql.Timestamp;

public class AverageCallRecordAttributeFilter {
    private Timestamp startDateFrom;
    private Timestamp startDateTo;
    private Timestamp endDateFrom;
    private Timestamp endDateTo;
    private Integer scale;

    public AverageCallRecordAttributeFilter(Timestamp startDateFrom, Timestamp startDateTo, Timestamp endDateFrom, Timestamp endDateTo, Integer scale) {
        this.startDateFrom = startDateFrom;
        this.startDateTo = startDateTo;
        this.endDateFrom = endDateFrom;
        this.endDateTo = endDateTo;
        this.scale = scale;
    }

    protected AverageCallRecordAttributeFilter() {
    }

    public Timestamp getStartDateFrom() {
        return startDateFrom;
    }

    public void setStartDateFrom(Timestamp startDateFrom) {
        this.startDateFrom = startDateFrom;
    }

    public Timestamp getStartDateTo() {
        return startDateTo;
    }

    public void setStartDateTo(Timestamp startDateTo) {
        this.startDateTo = startDateTo;
    }

    public Timestamp getEndDateFrom() {
        return endDateFrom;
    }

    public void setEndDateFrom(Timestamp endDateFrom) {
        this.endDateFrom = endDateFrom;
    }

    public Timestamp getEndDateTo() {
        return endDateTo;
    }

    public void setEndDateTo(Timestamp endDateTo) {
        this.endDateTo = endDateTo;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }
}
