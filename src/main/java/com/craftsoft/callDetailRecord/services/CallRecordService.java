package com.craftsoft.callDetailRecord.services;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.details.page.PagedListWithDetails;

import java.util.List;
import java.util.UUID;

public interface CallRecordService {

    List<CallRecordDetails> save(List<CallRecordDetails> detailsList);

    PagedListWithDetails<CallRecordDetails> list(CallRecordFilter filter);

    CallRecordDetails get(UUID uuid);

    void delete(UUID uuid);
}
