package com.craftsoft.callDetailRecord.services.impl;

import com.craftsoft.callDetailRecord.details.AverageCallRecordAttributeFilter;
import com.craftsoft.callDetailRecord.details.CallRecordDetails;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.details.page.PagedListWithDetails;
import com.craftsoft.callDetailRecord.repositories.CallRecordRepository;
import com.craftsoft.callDetailRecord.services.CallRecordService;
import com.craftsoft.callDetailRecord.utils.CallRecordUtils;
import com.craftsoft.callDetailRecord.utils.ExceptionMessage;
import com.craftsoft.callDetailRecord.utils.page.PageUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CallRecordServiceImpl implements CallRecordService {

    Logger logger = LoggerFactory.getLogger(CallRecordServiceImpl.class);

    @Autowired
    private CallRecordRepository repository;

    @Override
    public List<CallRecordDetails> save(List<CallRecordDetails> detailsList) {
        List<String> exceptionMessageList = new ArrayList<>();
        if (CollectionUtils.isEmpty(detailsList)){
            logger.error(ExceptionMessage.DETAILS_LIST_NULL);
            throw new RuntimeException(ExceptionMessage.DETAILS_LIST_NULL);
        }
        var entityList = detailsList.stream()
                .filter(x-> {
                    var exceptionListForCall = getExceptionMessages(x);
                    exceptionMessageList.addAll(exceptionListForCall);
                    return CollectionUtils.isEmpty(exceptionListForCall);
                })
                .map(CallRecordUtils::toEntity)
                .collect(Collectors.toList());

        entityList = repository.saveAll(entityList);

        if (CollectionUtils.isNotEmpty(exceptionMessageList)){
            var exceptionMessage = String.join(",", exceptionMessageList);
            logger.error(exceptionMessage);
            throw new RuntimeException(exceptionMessage);
        }

        var callRecordDetailsList = entityList.stream().map(CallRecordUtils::toDetails).collect(Collectors.toList());
        return callRecordDetailsList;
    }

    private List<String> getExceptionMessages(CallRecordDetails callRecordDetails){
        var exceptionList = new ArrayList<String>();
        if (callRecordDetails == null){
            exceptionList.add(ExceptionMessage.CALL_RECORD_NULL);
        }else {
            if (StringUtils.isEmpty(callRecordDetails.getAccount())){
                exceptionList.add(String.format(ExceptionMessage.CALL_RECORD_ACCOUNT_NULL, callRecordDetails.getUuid()));
            }
            if (StringUtils.isEmpty(callRecordDetails.getDestination())){
                exceptionList.add(String.format(ExceptionMessage.CALL_RECORD_DESTINATION_NULL, callRecordDetails.getUuid()));
            }
            if (callRecordDetails.getStartDate() == null){
                exceptionList.add(String.format(ExceptionMessage.CALL_RECORD_START_DATE_NULL, callRecordDetails.getUuid()));
            }
            if (callRecordDetails.getEndDate() == null) {
                exceptionList.add(String.format(ExceptionMessage.CALL_RECORD_END_DATE_NULL, callRecordDetails.getUuid()));
            }
            if (callRecordDetails.getStatus() == null){
                exceptionList.add(String.format(ExceptionMessage.CALL_RECORD_STATUS_NULL, callRecordDetails.getUuid()));
            }
            if (callRecordDetails.getCostPerMinute() == null){
                exceptionList.add(String.format(ExceptionMessage.CALL_RECORD_COST_PER_MINUTE_NULL, callRecordDetails.getUuid()));
            }
        }
        return exceptionList;
    }

    @Override
    public PagedListWithDetails<CallRecordDetails> list(CallRecordFilter filter) {
        if (filter == null){
            logger.error(ExceptionMessage.FILTER_NULL);
            throw new RuntimeException(ExceptionMessage.FILTER_NULL);
        }
        var callRecordPage = repository.list(filter);
        var pagedDetails = PageUtils.newPagedListWithDetails(callRecordPage, CallRecordUtils::toDetails);
        return pagedDetails;
    }

    @Override
    public CallRecordDetails get(UUID uuid) {
        var callRecord = repository.findById(uuid).orElse(null);
        return callRecord != null ? CallRecordUtils.toDetails(callRecord) : null;
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public BigDecimal averageCallCost(AverageCallRecordAttributeFilter filter) {
        var averageCallCost = repository.averageCallCost(filter);
        if (averageCallCost == null){
            return null;
        }
        var averageCallCostBigDecimal = BigDecimal.valueOf(averageCallCost);
        averageCallCostBigDecimal = filter.getScale() != null ? averageCallCostBigDecimal.setScale(filter.getScale(), RoundingMode.HALF_UP) : averageCallCostBigDecimal.setScale(2, RoundingMode.HALF_UP);
        return averageCallCostBigDecimal;
    }
}
