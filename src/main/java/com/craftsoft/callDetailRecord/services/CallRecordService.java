package com.craftsoft.callDetailRecord.services;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CallRecordService {

    List<CallRecordDetails> save(List<CallRecordDetails> detailsList);
}
