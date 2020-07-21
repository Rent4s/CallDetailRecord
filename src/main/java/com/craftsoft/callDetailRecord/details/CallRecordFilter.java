package com.craftsoft.callDetailRecord.details;

import com.craftsoft.callDetailRecord.utils.enums.StatusEnum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class CallRecordFilter {

    private List<UUID> uuidList;
    private List<String> accountList;
    private List<String> destinationList;
    private Timestamp startDateFrom;
    private Timestamp startDateTo;
    private Timestamp endDateFrom;
    private Timestamp endDateTo;
    private List<StatusEnum> statusList;
    private BigDecimal costPerMinuteFrom;
    private BigDecimal costPerMinuteTo;

    public CallRecordFilter(List<String> accountList, List<String> destinationList, Timestamp startDateFrom, Timestamp startDateTo, Timestamp endDateFrom, Timestamp endDateTo, List<StatusEnum> statusList, List<UUID> uuidList, BigDecimal costPerMinuteFrom, BigDecimal costPerMinuteTo) {
        this.accountList = accountList;
        this.destinationList = destinationList;
        this.startDateFrom = startDateFrom;
        this.startDateTo = startDateTo;
        this.endDateFrom = endDateFrom;
        this.endDateTo = endDateTo;
        this.statusList = statusList;
        this.uuidList = uuidList;
        this.costPerMinuteFrom = costPerMinuteFrom;
        this.costPerMinuteTo = costPerMinuteTo;
    }

    protected CallRecordFilter() {
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<String> accountList) {
        this.accountList = accountList;
    }

    public List<String> getDestinationList() {
        return destinationList;
    }

    public void setDestinationList(List<String> destinationList) {
        this.destinationList = destinationList;
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

    public List<StatusEnum> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusEnum> statusList) {
        this.statusList = statusList;
    }

    public List<UUID> getUuidList() {
        return uuidList;
    }

    public void setUuidList(List<UUID> uuidList) {
        this.uuidList = uuidList;
    }

    public BigDecimal getCostPerMinuteFrom() {
        return costPerMinuteFrom;
    }

    public void setCostPerMinuteFrom(BigDecimal costPerMinuteFrom) {
        this.costPerMinuteFrom = costPerMinuteFrom;
    }

    public BigDecimal getCostPerMinuteTo() {
        return costPerMinuteTo;
    }

    public void setCostPerMinuteTo(BigDecimal costPerMinuteTo) {
        this.costPerMinuteTo = costPerMinuteTo;
    }
}
