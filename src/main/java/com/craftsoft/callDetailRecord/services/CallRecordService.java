package com.craftsoft.callDetailRecord.services;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CallRecordService {

    List<CallRecordDetails> save(List<CallRecordDetails> detailsList);

    List<CallRecordDetails> list(CallRecordFilter filter);
}
