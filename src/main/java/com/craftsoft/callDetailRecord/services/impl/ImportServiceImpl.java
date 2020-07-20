package com.craftsoft.callDetailRecord.services.impl;

import com.craftsoft.callDetailRecord.services.ImportService;
import com.craftsoft.callDetailRecord.utils.ExceptionMessage;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {
    private final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);

    @Override
    public <T> List<T> loadObjectList(Class<T> type, MultipartFile file, String fileUrl, Character delimiter) throws Exception {
            var bytes = validate(file, fileUrl);
            CsvMapper mapper = new CsvMapper();
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(delimiter);
            MappingIterator<T> readValues =
                    mapper.readerWithSchemaFor(type).with(bootstrapSchema).readValues(bytes);
            return readValues.readAll();
    }

    private byte[] validate(MultipartFile file, String fileUrl) throws IOException {
        if (file == null && StringUtils.isEmpty(fileUrl)){
            throw new RuntimeException(ExceptionMessage.IMPORT_NO_FILE);
        }
        return file == null ? IOUtils.toByteArray(new URL(fileUrl)) : file.getBytes();
    }
}
