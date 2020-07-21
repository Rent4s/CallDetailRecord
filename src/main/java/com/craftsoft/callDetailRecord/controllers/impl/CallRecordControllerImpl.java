package com.craftsoft.callDetailRecord.controllers.impl;

import com.craftsoft.callDetailRecord.controllers.CallRecordController;
import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.services.CallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CallRecordControllerImpl implements CallRecordController {

    @Autowired
    private CallRecordService service;

    @Override
    public List<CallRecordDetails> save(List<CallRecordDetails> detailsList) {
        return service.save(detailsList);
    }

    @Override
    public List<CallRecordDetails> list(CallRecordFilter filter) {
        return service.list(filter);
    }
}
