package com.craftsoft.callDetailRecord.entity;

import com.craftsoft.callDetailRecord.utils.enums.StatusEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class CallRecord {

    private UUID uuid;
    private String account;
    private String destination;
    private Timestamp startDate;
    private Timestamp endDate;
    private StatusEnum status;
    private BigDecimal costPerMinute;

    protected CallRecord(){

    }
    public CallRecord(UUID uuid, String account, String destination, Timestamp startDate, Timestamp endDate, StatusEnum status, BigDecimal costPerMinute) {
        this.uuid = uuid;
        this.account = account;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.costPerMinute = costPerMinute;
    }

    @Id
    @GenericGenerator(name = "IdentityGeneratorForExisting", strategy = "com.craftsoft.callDetailRecord.configuration.hibernate.IdentityGeneratorForExisting")
    @GeneratedValue(generator = "IdentityGeneratorForExisting")
    @Column(unique = true, nullable = false)
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Column(nullable = false)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(nullable = false)
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Column(nullable = false)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Column(nullable = false)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Column(nullable = false)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Column(nullable = false)
    public BigDecimal getCostPerMinute() {
        return costPerMinute;
    }

    public void setCostPerMinute(BigDecimal costPerMinute) {
        this.costPerMinute = costPerMinute;
    }
}
