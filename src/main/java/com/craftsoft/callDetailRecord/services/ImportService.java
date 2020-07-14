package com.craftsoft.callDetailRecord.services;

import java.io.File;
import java.util.List;

public interface ImportService {
    <T> List<T> loadObjectList(Class<T> type, File file, String fileName, Character delimiter);
}
