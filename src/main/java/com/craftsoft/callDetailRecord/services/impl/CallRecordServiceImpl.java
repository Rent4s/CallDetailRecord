package com.craftsoft.callDetailRecord.services.impl;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.repositories.CallRecordRepository;
import com.craftsoft.callDetailRecord.services.CallRecordService;
import com.craftsoft.callDetailRecord.utils.CallRecordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CallRecordServiceImpl implements CallRecordService {

    @Autowired
    private CallRecordRepository repository;

    @Override
    public List<CallRecordDetails> save(List<CallRecordDetails> detailsList) {
        var callRecordList = detailsList.stream().map(CallRecordUtils::toEntity).collect(Collectors.toList());
        callRecordList = repository.saveAll(callRecordList);
        var callRecordDetailsList = callRecordList.stream().map(CallRecordUtils::toDetails).collect(Collectors.toList());
        return callRecordDetailsList;
    }

    @Override
    public List<CallRecordDetails> list(CallRecordFilter filter) {
        var callRecordList = repository.list(filter);
        var callRecordsDetailsList =  callRecordList.stream().map(CallRecordUtils::toDetails).collect(Collectors.toList());
        return callRecordsDetailsList;
    }
}
