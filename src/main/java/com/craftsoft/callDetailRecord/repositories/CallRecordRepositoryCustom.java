package com.craftsoft.callDetailRecord.repositories;

import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.entity.CallRecord;
import org.springframework.data.domain.Page;

public interface CallRecordRepositoryCustom {

    Page<CallRecord> list(CallRecordFilter filter);

}
