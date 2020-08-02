package com.craftsoft.callDetailRecord.utils;

import com.craftsoft.callDetailRecord.entity.CallRecord;
import com.craftsoft.callDetailRecord.utils.page.PageUtils;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PageUtilsTest {

    @Test
    public void newPagedListWithDetails(){
        var uuid = UUID.randomUUID();
        var callRecord = CallRecordUtils.newEntity(uuid);
        Page<CallRecord> page = new PageImpl<>(List.of(callRecord));
        var pagedListWithDetails = PageUtils.newPagedListWithDetails(page, CallRecordUtils::toDetails);
        var callRecordDetails = pagedListWithDetails.getList().get(0);

        assertEquals(1,pagedListWithDetails.getTotalElements());
        assertEquals(1,pagedListWithDetails.getPageSize());
        assertEquals(uuid, callRecordDetails.getUuid());
    }
}
