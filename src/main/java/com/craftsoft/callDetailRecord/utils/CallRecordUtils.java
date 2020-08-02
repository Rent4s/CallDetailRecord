package com.craftsoft.callDetailRecord.utils;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.entity.CallRecord;
import com.craftsoft.callDetailRecord.utils.enums.StatusEnum;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class CallRecordUtils {
    public static CallRecordDetails toDetails(CallRecord callRecord){
        return new CallRecordDetails(callRecord.getUuid(), callRecord.getAccount(), callRecord.getDestination(), callRecord.getStartDate(),
                callRecord.getEndDate(), callRecord.getStatus(), callRecord.getCostPerMinute());
    }

    public static CallRecord toEntity(CallRecordDetails callRecordDetails){
        return new CallRecord(callRecordDetails.getUuid(), callRecordDetails.getAccount(), callRecordDetails.getDestination(),
                callRecordDetails.getStartDate(), callRecordDetails.getEndDate(), callRecordDetails.getStatus(), callRecordDetails.getCostPerMinute());
    }


    public static CallRecordDetails newDetails(UUID uuid){
        return new CallRecordDetails(uuid, null, null, null, null, null, null);
    }

    public static CallRecordDetails newDetails(UUID uuid, String account, String destination, Timestamp startDate, Timestamp endDate,
                                               StatusEnum status, BigDecimal costPerMinute){
        return new CallRecordDetails(uuid, account, destination, startDate, endDate, status, costPerMinute);
    }

    public static CallRecord newEntity(UUID uuid, String account, String destination, Timestamp startDate, Timestamp endDate, StatusEnum status,
                                       BigDecimal costPerMinute){
        return new CallRecord(uuid, account, destination, startDate, endDate, status, costPerMinute);
    }

    public static CallRecord newEntity(UUID uuid){
        return new CallRecord(uuid, null, null, null, null, null, null);
    }

    public static CallRecordFilter newFilter(Integer page, Integer pageSize, String orderBy , Sort.Direction direction,
                                             List<Sort.Order> sortingOrderList, List<String> accountList, List<String> destinationList, Timestamp startDateFrom,
                                             Timestamp startDateTo, Timestamp endDateFrom, Timestamp endDateTo, List<StatusEnum> statusList, List<UUID> uuidList,
                                             BigDecimal costPerMinuteFrom, BigDecimal costPerMinuteTo){
        return new CallRecordFilter(page, pageSize, orderBy, direction, sortingOrderList, accountList, destinationList, startDateFrom, startDateTo,
                endDateFrom, endDateTo, statusList,uuidList, costPerMinuteFrom, costPerMinuteTo);
    }

    public static CallRecordFilter newFilter(Integer page, Integer pageSize){
        return newFilter(page, pageSize, null, null, null, null, null, null, null,
                null, null, null,null,null, null);
    }
}
