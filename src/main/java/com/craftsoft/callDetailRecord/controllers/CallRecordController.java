package com.craftsoft.callDetailRecord.controllers;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.details.page.PagedListWithDetails;
import com.craftsoft.callDetailRecord.utils.RestUrl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

public interface CallRecordController {

    @RequestMapping(value = RestUrl.callRecords, method = RequestMethod.PUT)
    List<CallRecordDetails> save(@RequestBody List<CallRecordDetails> detailsList);

    @RequestMapping(value = RestUrl.callRecords, method = RequestMethod.POST)
    PagedListWithDetails<CallRecordDetails> list(@RequestBody CallRecordFilter filter);

    @RequestMapping(value = RestUrl.callRecordByUuid, method = RequestMethod.GET)
    CallRecordDetails get(@PathVariable("uuid") UUID uuid);


    @RequestMapping(value = RestUrl.callRecordByUuid, method = RequestMethod.DELETE)
    void delete(@PathVariable("uuid") UUID uuid);

}
