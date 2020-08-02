package com.craftsoft.callDetailRecord.utils;

import com.craftsoft.callDetailRecord.details.AverageCallRecordAttributeFilter;

import java.sql.Timestamp;

public class AverageCallRecordUtils {
    public static AverageCallRecordAttributeFilter newFilter (Timestamp startDateFrom, Timestamp startDateTo, Timestamp endDateFrom, Timestamp endDateTo, Integer scale){
        return new AverageCallRecordAttributeFilter(startDateFrom, startDateTo, endDateFrom, endDateTo, scale);
    }
    public static AverageCallRecordAttributeFilter newFilter (){
        return new AverageCallRecordAttributeFilter(null, null, null,  null, null);
    }

}
