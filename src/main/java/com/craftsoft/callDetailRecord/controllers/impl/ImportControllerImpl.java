package com.craftsoft.callDetailRecord.controllers.impl;

import com.craftsoft.callDetailRecord.controllers.ImportController;
import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.services.CallRecordService;
import com.craftsoft.callDetailRecord.services.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class ImportControllerImpl implements ImportController {

    @Autowired
    private ImportService importService;

    @Autowired
    private CallRecordService callRecordService;

    @Override
    public List<CallRecordDetails> importCallRecordDetailsList(File file, String fileUrl) {
        var callRecordDetailsList = importService.loadObjectList(CallRecordDetails.class,file, fileUrl, ',');
        return callRecordService.save(callRecordDetailsList);
    }
}
