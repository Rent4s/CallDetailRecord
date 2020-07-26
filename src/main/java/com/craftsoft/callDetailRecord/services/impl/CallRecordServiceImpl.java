package com.craftsoft.callDetailRecord.services.impl;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.details.page.PagedListWithDetails;
import com.craftsoft.callDetailRecord.repositories.CallRecordRepository;
import com.craftsoft.callDetailRecord.services.CallRecordService;
import com.craftsoft.callDetailRecord.utils.CallRecordUtils;
import com.craftsoft.callDetailRecord.utils.ExceptionMessage;
import com.craftsoft.callDetailRecord.utils.page.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
    public PagedListWithDetails<CallRecordDetails> list(CallRecordFilter filter) {
        if (filter == null){
            throw new RuntimeException(ExceptionMessage.FILTER_NULL);
        }
        var callRecordPage = repository.list(filter);
        var pagedDetails = PageUtils.newPagedListWithDetails(callRecordPage, CallRecordUtils::toDetails);
        return pagedDetails;
    }

    @Override
    public CallRecordDetails get(UUID uuid) {
        var callRecord = repository.findById(uuid).orElse(null);
        return callRecord != null ? CallRecordUtils.toDetails(callRecord) : null;
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }
}
