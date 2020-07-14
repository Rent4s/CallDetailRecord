package com.craftsoft.callDetailRecord.details;

import com.craftsoft.callDetailRecord.utils.enums.StatusEnum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class CallRecordDetails {

    private UUID uuid;
    private String account;
    private String destination;
    private Timestamp startDate;
    private Timestamp endDate;
    private StatusEnum status;
    private BigDecimal costPerMinute;

    protected CallRecordDetails() {
    }

    public CallRecordDetails(UUID uuid, String account, String destination, Timestamp startDate, Timestamp endDate, StatusEnum status, BigDecimal costPerMinute) {
        this.uuid = uuid;
        this.account = account;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.costPerMinute = costPerMinute;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public BigDecimal getCostPerMinute() {
        return costPerMinute;
    }

    public void setCostPerMinute(BigDecimal costPerMinute) {
        this.costPerMinute = costPerMinute;
    }
}
