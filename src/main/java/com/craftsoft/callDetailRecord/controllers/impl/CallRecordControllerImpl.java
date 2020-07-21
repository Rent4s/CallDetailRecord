package com.craftsoft.callDetailRecord.controllers.impl;

import com.craftsoft.callDetailRecord.controllers.CallRecordController;
import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.services.CallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @Override
    public CallRecordDetails get(UUID uuid) {
        return service.get(uuid);
    }

    @Override
    public void delete(UUID uuid) {
        service.delete(uuid);
    }
}
