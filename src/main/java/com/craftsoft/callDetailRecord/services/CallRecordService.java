package com.craftsoft.callDetailRecord.services;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface CallRecordService {

    List<CallRecordDetails> save(List<CallRecordDetails> detailsList);

    List<CallRecordDetails> list(CallRecordFilter filter);

    CallRecordDetails get(UUID uuid);

    void delete(UUID uuid);
}
