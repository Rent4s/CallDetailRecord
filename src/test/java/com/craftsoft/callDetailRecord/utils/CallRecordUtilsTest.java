package com.craftsoft.callDetailRecord.utils;

import com.craftsoft.callDetailRecord.utils.enums.StatusEnum;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CallRecordUtilsTest {

    private UUID uuid = UUID.randomUUID();
    private Timestamp nowTimestamp = new Timestamp(new Date().getTime());
    private String account = "account";
    private String destination = "destination";
    private BigDecimal costPerMinute = new BigDecimal("9.1");

    @Test
    public void newDetails(){

        var callRecordDetails = CallRecordUtils.newDetails(uuid, account, destination, nowTimestamp, nowTimestamp, StatusEnum.SUCCESS, costPerMinute);

        assertEquals(uuid, callRecordDetails.getUuid());
        assertEquals(account, callRecordDetails.getAccount());
        assertEquals(destination, callRecordDetails.getDestination());
        assertEquals(nowTimestamp, callRecordDetails.getStartDate());
        assertEquals(nowTimestamp, callRecordDetails.getEndDate());
        assertEquals(costPerMinute, callRecordDetails.getCostPerMinute());
    }

    @Test
    public void newEntity(){
        var callRecord = CallRecordUtils.newEntity(uuid, account, destination, nowTimestamp, nowTimestamp, StatusEnum.SUCCESS, costPerMinute);

        assertEquals(uuid, callRecord.getUuid());
        assertEquals(account, callRecord.getAccount());
        assertEquals(destination, callRecord.getDestination());
        assertEquals(nowTimestamp, callRecord.getStartDate());
        assertEquals(nowTimestamp, callRecord.getEndDate());
        assertEquals(costPerMinute, callRecord.getCostPerMinute());
    }

    @Test
    public void toEntity(){
        var callRecordDetails = CallRecordUtils.newDetails(uuid, account, destination, nowTimestamp, nowTimestamp, StatusEnum.SUCCESS, costPerMinute);

        var callRecord = CallRecordUtils.toEntity(callRecordDetails);

        assertEquals(uuid, callRecord.getUuid());
        assertEquals(account, callRecord.getAccount());
        assertEquals(destination, callRecord.getDestination());
        assertEquals(nowTimestamp, callRecord.getStartDate());
        assertEquals(nowTimestamp, callRecord.getEndDate());
        assertEquals(costPerMinute, callRecord.getCostPerMinute());
    }

    @Test
    public void toDetails(){
        var callRecord = CallRecordUtils.newEntity(uuid, account, destination, nowTimestamp, nowTimestamp, StatusEnum.SUCCESS, costPerMinute);

        var callRecordDetails = CallRecordUtils.toDetails(callRecord);

        assertEquals(uuid, callRecordDetails.getUuid());
        assertEquals(account, callRecordDetails.getAccount());
        assertEquals(destination, callRecordDetails.getDestination());
        assertEquals(nowTimestamp, callRecordDetails.getStartDate());
        assertEquals(nowTimestamp, callRecordDetails.getEndDate());
        assertEquals(costPerMinute, callRecordDetails.getCostPerMinute());
    }

    @Test
    public void newFilter(){
        var sortingOrderList = List.of(new Sort.Order(Sort.Direction.ASC, "orderBy2"));
        Integer page = 1;
        Integer pageSize = 10;
        var asc = Sort.Direction.ASC;
        var orderBy = "orderBy";
        var accountList = List.of(account);
        var destinationList = List.of(destination);
        var success = List.of(StatusEnum.SUCCESS);
        var uuidList = List.of(UUID.randomUUID());

         var filter = CallRecordUtils.newFilter(page, pageSize, orderBy, asc, sortingOrderList, accountList, destinationList,
                nowTimestamp, nowTimestamp, nowTimestamp, nowTimestamp,
                success, uuidList, costPerMinute, costPerMinute);



        assertEquals(page, filter.getPage());
        assertEquals(pageSize, filter.getPageSize());
        assertEquals(orderBy, filter.getOrderBy());
        assertEquals(asc, filter.getDirection());
        assertEquals(destinationList, filter.getDestinationList());
        assertEquals(success, filter.getStatusList());
        assertEquals(accountList, filter.getAccountList());
        assertEquals(sortingOrderList, filter.getSortingOrderList());
        assertEquals(nowTimestamp, filter.getStartDateFrom());
        assertEquals(nowTimestamp, filter.getStartDateTo());
        assertEquals(nowTimestamp, filter.getEndDateFrom());
        assertEquals(nowTimestamp, filter.getEndDateTo());
        assertEquals(uuidList, filter.getUuidList());
        assertEquals(costPerMinute, filter.getCostPerMinuteFrom());
        assertEquals(costPerMinute, filter.getCostPerMinuteTo());

    }
}
