package com.craftsoft.callDetailRecord.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ImportService {
    <T> List<T> loadObjectList(Class<T> type, MultipartFile file, String fileName, Character delimiter) throws Exception;
}
