package com.craftsoft.callDetailRecord.controllers;

import com.craftsoft.callDetailRecord.controllers.integration.IntegrationTestBase;
import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.utils.RestUrl;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class ImportControllerTest extends IntegrationTestBase {

    @Value("${import.file}")
    private String fileLocation;

    @Test
    public void importCallRecordsFromFile() throws Exception {
        var content = IOUtils.toString(this.getClass().getResourceAsStream(fileLocation), StandardCharsets.UTF_8);
        var file = new MockMultipartFile("file", "data.txt", MediaType.MULTIPART_FORM_DATA_VALUE, content.getBytes());

        List<CallRecordDetails> list = httpPostFileSuccess(RestUrl.importCallRecordDetailsList, List.class, CallRecordDetails.class, null, file);
        assertEquals(4, list.size());
        var uuidList = list.stream().map(CallRecordDetails::getUuid).collect(Collectors.toList());
        assertTrue(uuidList.contains(UUID.fromString("97c491f3-707f-4929-866d-4a1f37008b40")));
        assertTrue(uuidList.contains(UUID.fromString("2cdd0cd7-6b30-4cf3-a896-9de30e2b64e0")));
        assertTrue(uuidList.contains(UUID.fromString("4c9b554b-df47-46ee-abbc-9f663432516c")));
        assertTrue(uuidList.contains(UUID.fromString("7eebd1ee-3264-486c-b0fd-6072f064127e")));
    }

    @Test
    public void importCallRecordsFromUrl() throws Exception {
        URL url = this.getClass().getResource(fileLocation);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("fileUrl", List.of(url.toString()));

        List<CallRecordDetails> list = httpPostSuccess(RestUrl.importCallRecordDetailsList, List.class, CallRecordDetails.class, null, params);
        assertEquals(4, list.size());
        var uuidList = list.stream().map(CallRecordDetails::getUuid).collect(Collectors.toList());
        assertTrue(uuidList.contains(UUID.fromString("97c491f3-707f-4929-866d-4a1f37008b40")));
        assertTrue(uuidList.contains(UUID.fromString("2cdd0cd7-6b30-4cf3-a896-9de30e2b64e0")));
        assertTrue(uuidList.contains(UUID.fromString("4c9b554b-df47-46ee-abbc-9f663432516c")));
        assertTrue(uuidList.contains(UUID.fromString("7eebd1ee-3264-486c-b0fd-6072f064127e")));
    }
}
