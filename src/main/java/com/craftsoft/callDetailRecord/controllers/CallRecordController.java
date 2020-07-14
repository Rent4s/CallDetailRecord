package com.craftsoft.callDetailRecord.controllers;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.utils.RestUrl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface CallRecordController {

    @RequestMapping(value = RestUrl.callRecord, method = RequestMethod.POST)
    List<CallRecordDetails> save(@RequestBody List<CallRecordDetails> detailsList);
}
