package com.craftsoft.callDetailRecord.controllers;

import com.craftsoft.callDetailRecord.details.AverageCallRecordAttributeFilter;
import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.details.page.PagedListWithDetails;
import com.craftsoft.callDetailRecord.utils.RestUrl;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CallRecordController {

    @RequestMapping(value = RestUrl.callRecords, method = RequestMethod.PUT)
    List<CallRecordDetails> save(@RequestBody List<CallRecordDetails> detailsList) throws Exception;

    @RequestMapping(value = RestUrl.callRecords, method = RequestMethod.POST)
    PagedListWithDetails<CallRecordDetails> list(@RequestBody CallRecordFilter filter);

    @RequestMapping(value = RestUrl.callRecordByUuid, method = RequestMethod.GET)
    CallRecordDetails get(@PathVariable("uuid") UUID uuid);


    @RequestMapping(value = RestUrl.callRecordByUuid, method = RequestMethod.DELETE)
    void delete(@PathVariable("uuid") UUID uuid);

    @RequestMapping(value = RestUrl.averageCallCost, method = RequestMethod.POST)
    BigDecimal averageCallCost(@RequestBody  AverageCallRecordAttributeFilter filter);
}
