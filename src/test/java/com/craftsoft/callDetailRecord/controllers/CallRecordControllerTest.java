package com.craftsoft.callDetailRecord.controllers;

import com.craftsoft.callDetailRecord.controllers.integration.IntegrationTestBase;
import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.details.page.PagedListWithDetails;
import com.craftsoft.callDetailRecord.entity.CallRecord;
import com.craftsoft.callDetailRecord.repositories.CallRecordRepository;
import com.craftsoft.callDetailRecord.services.CallRecordService;
import com.craftsoft.callDetailRecord.utils.AverageCallRecordUtils;
import com.craftsoft.callDetailRecord.utils.CallRecordUtils;
import com.craftsoft.callDetailRecord.utils.ExceptionMessage;
import com.craftsoft.callDetailRecord.utils.RestUrl;
import com.craftsoft.callDetailRecord.utils.enums.StatusEnum;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class CallRecordControllerTest extends IntegrationTestBase {

    private CallRecord callRecord;
    private CallRecord callRecord2;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp startDate2;
    private Timestamp endDate2;
    @Autowired
    private CallRecordRepository callRecordRepository;
    @Autowired
    private CallRecordService callRecordService;


    @Before
    public void setUp() throws Exception {
        startDate = Timestamp.valueOf("2020-07-29 12:30:00");
        endDate = Timestamp.valueOf("2020-07-29 12:31:00");
        startDate2 = Timestamp.valueOf("2020-08-05 02:30:00");
        endDate2 = Timestamp.valueOf("2020-08-05 02:31:00");
        callRecord = CallRecordUtils.newEntity(null, "account", "destination", startDate, endDate, StatusEnum.SUCCESS, new BigDecimal("0.1"));
        callRecord2 = CallRecordUtils.newEntity(null, "account2", "destination2", startDate2, endDate2, StatusEnum.FAILED, new BigDecimal("0.78"));
        callRecordRepository.deleteAll();
    }

    @Test
    public void save_new() throws Exception {
        assertEquals(0, callRecordRepository.findAll().size());

        var callRecordDetails = CallRecordUtils.toDetails(callRecord);
        var list = httpPutSuccess(RestUrl.callRecords, List.class, CallRecordDetails.class, List.of(callRecordDetails));
        var callRecordEntityList = callRecordRepository.findAll();

        assertEquals(1, list.size());
        assertEquals(1, callRecordEntityList.size());

        callRecordDetails = (CallRecordDetails) list.get(0);
        callRecord = callRecordEntityList.get(0);

        assertEquals(callRecord.getUuid(), callRecordDetails.getUuid());
        assertEquals(callRecord.getAccount(), callRecordDetails.getAccount());
        assertEquals(callRecord.getDestination(), callRecordDetails.getDestination());
        assertEquals(callRecord.getStartDate(), callRecordDetails.getStartDate());
        assertEquals(callRecord.getEndDate(), callRecordDetails.getEndDate());
        assertEquals(0, callRecord.getCostPerMinute().compareTo(callRecordDetails.getCostPerMinute()));

    }

    @Test
    public void save_updateExisting() throws Exception {
        callRecord = callRecordRepository.save(callRecord);
        assertEquals(1, callRecordRepository.findAll().size());

        var callRecordDetails = CallRecordUtils.toDetails(callRecord2);
        callRecordDetails.setUuid(callRecord.getUuid());

        var list = httpPutSuccess(RestUrl.callRecords, List.class, CallRecordDetails.class, List.of(callRecordDetails));
        var callRecordEntityList = callRecordRepository.findAll();

        assertEquals(1, list.size());
        assertEquals(1, callRecordEntityList.size());

        callRecordDetails = (CallRecordDetails) list.get(0);


        assertEquals(callRecord.getUuid(), callRecordDetails.getUuid());
        assertEquals(callRecord2.getAccount(), callRecordDetails.getAccount());
        assertEquals(callRecord2.getDestination(), callRecordDetails.getDestination());
        assertEquals(callRecord2.getStartDate(), callRecordDetails.getStartDate());
        assertEquals(callRecord2.getEndDate(), callRecordDetails.getEndDate());
        assertEquals(0, callRecord2.getCostPerMinute().compareTo(callRecordDetails.getCostPerMinute()));
    }

    @Test
    public void save_throwValidationErrors() throws Exception {

        httpPutError(RestUrl.callRecords, new ArrayList<>(), List.of(ExceptionMessage.DETAILS_LIST_NULL));

        var callRecordDetails = CallRecordUtils.newDetails(null);

        var exceptionMessageList = new ArrayList<String>();
        exceptionMessageList.add(String.format(ExceptionMessage.CALL_RECORD_ACCOUNT_NULL, callRecordDetails.getUuid()));
        exceptionMessageList.add(String.format(ExceptionMessage.CALL_RECORD_DESTINATION_NULL, callRecordDetails.getUuid()));
        exceptionMessageList.add(String.format(ExceptionMessage.CALL_RECORD_STATUS_NULL, callRecordDetails.getUuid()));
        exceptionMessageList.add(String.format(ExceptionMessage.CALL_RECORD_START_DATE_NULL, callRecordDetails.getUuid()));
        exceptionMessageList.add(String.format(ExceptionMessage.CALL_RECORD_END_DATE_NULL, callRecordDetails.getUuid()));
        exceptionMessageList.add(String.format(ExceptionMessage.CALL_RECORD_COST_PER_MINUTE_NULL, callRecordDetails.getUuid()));
        exceptionMessageList.add(ExceptionMessage.CALL_RECORD_NULL);

        httpPutError(RestUrl.callRecords, Arrays.asList(callRecordDetails, null), exceptionMessageList);
    }

    @Test
    public void get() throws Exception{
        callRecord = callRecordRepository.save(callRecord);

        var callRecordDetails = httpGetSuccess(RestUrl.callRecordByUuid, CallRecordDetails.class, null, callRecord.getUuid().toString());
        assertNotNull(callRecordDetails);
        assertEquals(callRecord.getUuid(), callRecordDetails.getUuid());
    }

    @Test
    public void delete() throws Exception{
        callRecord = callRecordRepository.save(callRecord);
        assertEquals(1, callRecordRepository.findAll().size());

        httpDeleteSuccess(RestUrl.callRecordByUuid, null, callRecord.getUuid().toString());

        assertEquals(0, callRecordRepository.findAll().size());
    }

    @Test
    public void averageCallCost() throws Exception{
        callRecord = callRecordRepository.save(callRecord);
        callRecord2 = callRecordRepository.save(callRecord2);

        var filter = AverageCallRecordUtils.newFilter();
        var average = httpPostSuccess(RestUrl.averageCallCost, BigDecimal.class,  filter);
        assertEquals(new BigDecimal("0.44"), average);

        filter.setScale(1);
        average = httpPostSuccess(RestUrl.averageCallCost, BigDecimal.class,  filter);
        assertEquals(new BigDecimal("0.4"), average);


        filter = AverageCallRecordUtils.newFilter();
        filter.setStartDateFrom(startDate2);
        average = httpPostSuccess(RestUrl.averageCallCost, BigDecimal.class,  filter);
        assertEquals(new BigDecimal("0.78"), average);


        filter = AverageCallRecordUtils.newFilter();
        filter.setStartDateTo(startDate);
        average = httpPostSuccess(RestUrl.averageCallCost, BigDecimal.class,  filter);
        assertEquals(new BigDecimal("0.10"), average);


        filter = AverageCallRecordUtils.newFilter();
        filter.setEndDateFrom(endDate2);
        average = httpPostSuccess(RestUrl.averageCallCost, BigDecimal.class,  filter);
        assertEquals(new BigDecimal("0.78"), average);

        filter = AverageCallRecordUtils.newFilter();
        filter.setEndDateTo(endDate);
        average = httpPostSuccess(RestUrl.averageCallCost, BigDecimal.class,  filter);
        assertEquals(new BigDecimal("0.10"), average);
    }

    @Test
    public void list() throws Exception {
        callRecord = callRecordRepository.save(callRecord);
        callRecord2 = callRecordRepository.save(callRecord2);

        var filter = CallRecordUtils.newFilter(1,10);
        filter.setOrderBy("account");
        filter.setDirection(Sort.Direction.ASC);
        var list = callList(filter, 2);
        assertEquals(callRecord.getUuid(), list.get(0).getUuid());

        filter = CallRecordUtils.newFilter(1, 10);
        filter.setAccountList(List.of("account"));
        list = callList(filter, 1);
        var callRecordDetails = list.get(0);
        assertEquals(callRecord.getUuid(), callRecordDetails.getUuid());

        filter = CallRecordUtils.newFilter(1, 10);
        filter.setAccountList(List.of("account", "account2"));
        list = callList(filter, 2);

        filter = CallRecordUtils.newFilter(1,10);
        filter.setDestinationList(List.of("destination2"));
        list = callList(filter, 1);
        callRecordDetails = list.get(0);
        assertEquals(callRecord2.getUuid(), callRecordDetails.getUuid());

        filter = CallRecordUtils.newFilter(1, 10);
        filter.setDestinationList(List.of("destination", "destination2"));
        list = callList(filter, 2);

        filter = CallRecordUtils.newFilter(1,10);
        filter.setStatusList(List.of(StatusEnum.FAILED));
        list = callList(filter, 1);
        callRecordDetails = list.get(0);
        assertEquals(callRecord2.getUuid(), callRecordDetails.getUuid());

        filter = CallRecordUtils.newFilter(1, 10);
        filter.setStatusList(List.of(StatusEnum.SUCCESS, StatusEnum.FAILED));
        list = callList(filter, 2);

        filter = CallRecordUtils.newFilter(1,10);
        filter.setCostPerMinuteFrom(new BigDecimal("0.6"));
        list = callList(filter, 1);
        callRecordDetails = list.get(0);
        assertEquals(callRecord2.getUuid(), callRecordDetails.getUuid());

        filter = CallRecordUtils.newFilter(1,10);
        filter.setCostPerMinuteTo(new BigDecimal("0.6"));
        list = callList(filter, 1);
        callRecordDetails = list.get(0);
        assertEquals(callRecord.getUuid(), callRecordDetails.getUuid());

        filter = CallRecordUtils.newFilter(1,10);
        filter.setUuidList(List.of(callRecord.getUuid()));
        list = callList(filter, 1);
        callRecordDetails = list.get(0);
        assertEquals(callRecord.getUuid(), callRecordDetails.getUuid());

        filter = CallRecordUtils.newFilter(1, 10);
        filter.setUuidList(List.of(callRecord.getUuid(), callRecord2.getUuid()));
        list = callList(filter, 2);

        filter = CallRecordUtils.newFilter(1,10);
        filter.setStartDateFrom(startDate);
        list = callList(filter, 2);

        filter = CallRecordUtils.newFilter(1,10);
        filter.setStartDateTo(startDate);
        list = callList(filter, 1);
        callRecordDetails = list.get(0);
        assertEquals(callRecord.getUuid(), callRecordDetails.getUuid());

        filter = CallRecordUtils.newFilter(1,10);
        filter.setEndDateFrom(endDate2);
        list = callList(filter, 1);
        callRecordDetails = list.get(0);
        assertEquals(callRecord2.getUuid(), callRecordDetails.getUuid());

        filter = CallRecordUtils.newFilter(1,10);
        filter.setEndDateTo(endDate2);
        list = callList(filter, 2);
    }

    private List<CallRecordDetails> callList(CallRecordFilter filter, int assertSize) throws Exception {
        var list = httpPostSuccess(RestUrl.callRecords, PagedListWithDetails.class, CallRecordDetails.class, filter).getList();
        assertEquals(assertSize, list.size());
        return list;
    }


}
