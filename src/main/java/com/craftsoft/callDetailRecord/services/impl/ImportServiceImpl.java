package com.craftsoft.callDetailRecord.services.impl;

import com.craftsoft.callDetailRecord.services.ImportService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {
    private Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);

    @Override
    public <T> List<T> loadObjectList(Class<T> type, File file, String fileUrl, Character delimiter) {
        if (file== null && StringUtils.isEmpty(fileUrl)){
            throw new RuntimeException("Either a file, or a file url must be provided");
        }
        try {
            if (file == null){
                file = new ClassPathResource(fileUrl).getFile();
            }
            CsvMapper mapper = new CsvMapper();
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(delimiter);
            MappingIterator<T> readValues =
                    mapper.readerWithSchemaFor(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            logger.error(String.format("Error occurred while loading object list from file %s", fileUrl), e);
            return Collections.emptyList();
        }
    }
}
