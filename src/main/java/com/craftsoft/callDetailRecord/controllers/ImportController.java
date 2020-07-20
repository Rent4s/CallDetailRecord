package com.craftsoft.callDetailRecord.controllers;

import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.utils.RestUrl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ImportController {

    @RequestMapping(value = RestUrl.importCallRecordDetailsList, method = RequestMethod.POST)
    List<CallRecordDetails> importCallRecordDetailsList(@RequestParam(required = false) MultipartFile file, @RequestParam(required = false) String fileUrl) throws Exception;
}
