package com.craftsoft.callDetailRecord.repositories;

import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.entity.CallRecord;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CallRecordRepositoryCustom {

    List<CallRecord> list(CallRecordFilter filter);

}
