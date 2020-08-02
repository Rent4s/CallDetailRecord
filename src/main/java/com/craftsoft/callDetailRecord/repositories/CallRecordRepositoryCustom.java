package com.craftsoft.callDetailRecord.repositories;

import com.craftsoft.callDetailRecord.details.AverageCallRecordAttributeFilter;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.entity.CallRecord;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;

public interface CallRecordRepositoryCustom {

    Page<CallRecord> list(CallRecordFilter filter);

    Double averageCallCost(AverageCallRecordAttributeFilter filter);
}
