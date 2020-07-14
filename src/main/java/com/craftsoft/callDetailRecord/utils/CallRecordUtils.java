package com.craftsoft.callDetailRecord.utils;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.entity.CallRecord;
import com.craftsoft.callDetailRecord.utils.enums.StatusEnum;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

public class CallRecordUtils {
    public static CallRecordDetails toDetails(CallRecord callRecord){
        return new CallRecordDetails(callRecord.getUuid(), callRecord.getAccount(), callRecord.getDestination(), callRecord.getStartDate(), callRecord.getEndDate(), callRecord.getStatus(), callRecord.getCostPerMinute());
    }

    public static CallRecord toEntity(CallRecordDetails callRecordDetails){
        return new CallRecord(callRecordDetails.getUuid(), callRecordDetails.getAccount(), callRecordDetails.getDestination(), callRecordDetails.getStartDate(), callRecordDetails.getEndDate(), callRecordDetails.getStatus(), callRecordDetails.getCostPerMinute());
    }


    public static CallRecordDetails newDetails(UUID uuid, String account, String destination, Timestamp startDate, Timestamp endDate, StatusEnum status, BigDecimal costPerMinute){
        return new CallRecordDetails(uuid, account, destination, startDate, endDate, status, costPerMinute);
    }

    public static CallRecord newEntity(UUID uuid, String account, String destination, Timestamp startDate, Timestamp endDate, StatusEnum status, BigDecimal costPerMinute){
        return new CallRecord(uuid, account, destination, startDate, endDate, status, costPerMinute);
    }
}
