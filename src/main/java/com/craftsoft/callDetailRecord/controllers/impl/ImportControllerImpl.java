package com.craftsoft.callDetailRecord.controllers.impl;

import com.craftsoft.callDetailRecord.controllers.ImportController;
import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.services.CallRecordService;
import com.craftsoft.callDetailRecord.services.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class ImportControllerImpl implements ImportController {

    @Autowired
    private ImportService importService;

    @Autowired
    private CallRecordService callRecordService;

    @Override
    public List<CallRecordDetails> importCallRecordDetailsList(MultipartFile file, String fileUrl) throws Exception {
        var delimiter = ',';
        var callRecordDetailsList = importService.loadObjectList(CallRecordDetails.class,file, fileUrl, delimiter);
        callRecordDetailsList.forEach(x-> {
            var startDateSecondTimestamp = x.getStartDate();
            var startDateMilliTimestamp = getMilliTimeStamp(startDateSecondTimestamp);
            x.setStartDate(startDateMilliTimestamp);

            var endDateSecondTimestamp = x.getEndDate();
            var endDateMilliTimestamp = getMilliTimeStamp(endDateSecondTimestamp);
            x.setEndDate(endDateMilliTimestamp);
        });
        return callRecordService.save(callRecordDetailsList);
    }

    private Timestamp getMilliTimeStamp(Timestamp secondTimeStamp) {
        var secondCount = secondTimeStamp.getTime();
        var milliCount = TimeUnit.SECONDS.toMillis(secondCount);
        return new Timestamp(milliCount);
    }
}
