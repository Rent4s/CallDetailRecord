package com.craftsoft.callDetailRecord.repositories.impl;

import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.entity.CallRecord;
import com.craftsoft.callDetailRecord.entity.CallRecord_;
import com.craftsoft.callDetailRecord.repositories.CallRecordRepositoryCustom;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CallRecordRepositoryImpl implements CallRecordRepositoryCustom {

    @Autowired
    EntityManager entityManager;


    @Override
    public Page<CallRecord> list(CallRecordFilter filter) {

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(CallRecord.class);

        var root = criteriaQuery.from(CallRecord.class);

        List<Predicate> predicateList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(filter.getUuidList())){
            var predicate = root.get(CallRecord_.uuid).in(filter.getUuidList());
            predicateList.add(predicate);
        }
        if (CollectionUtils.isNotEmpty(filter.getAccountList())){
            var predicate = root.get(CallRecord_.account).in(filter.getAccountList());
            predicateList.add(predicate);
        }
        if (CollectionUtils.isNotEmpty(filter.getDestinationList())){
            var predicate = root.get(CallRecord_.destination).in(filter.getDestinationList());
            predicateList.add(predicate);
        }
        if (filter.getCostPerMinuteFrom() != null){
            var predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(CallRecord_.costPerMinute), filter.getCostPerMinuteFrom());
            predicateList.add(predicate);
        }
        if (filter.getCostPerMinuteTo() != null){
            var predicate = criteriaBuilder.lessThanOrEqualTo(root.get(CallRecord_.costPerMinute), filter.getCostPerMinuteTo());
            predicateList.add(predicate);
        }
        if (CollectionUtils.isNotEmpty(filter.getStatusList())){
            var predicate = root.get(CallRecord_.status).in(filter.getStatusList());
            predicateList.add(predicate);
        }
        if (filter.getStartDateFrom() != null){
            var predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(CallRecord_.startDate), filter.getStartDateFrom());
            predicateList.add(predicate);
        }
        if (filter.getStartDateTo() != null){
            var predicate = criteriaBuilder.lessThanOrEqualTo(root.get(CallRecord_.startDate), filter.getStartDateTo());
            predicateList.add(predicate);
        }
        if (filter.getEndDateFrom() != null){
            var predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(CallRecord_.endDate), filter.getEndDateFrom());
            predicateList.add(predicate);
        }
        if (filter.getEndDateTo() != null){
            var predicate = criteriaBuilder.lessThanOrEqualTo(root.get(CallRecord_.endDate), filter.getEndDateTo());
            predicateList.add(predicate);
        }

        var predicates = predicateList.toArray(new Predicate[]{});
        criteriaQuery.where(predicates);
        criteriaQuery.distinct(true);

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var pageable = getPageRequest(filter);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        var results = typedQuery.getResultList();
        var totalElements = getTotalElements(criteriaBuilder, predicates);

        return new PageImpl<>(results, pageable, totalElements);
    }

    private Long getTotalElements(CriteriaBuilder criteriaBuilder, Predicate[] predicates) {
        var countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(CallRecord.class)));
        countCriteriaQuery.where(predicates);
        countCriteriaQuery.distinct(true);
        return entityManager.createQuery(countCriteriaQuery).getSingleResult();
    }

    private Pageable getPageRequest(CallRecordFilter filter) {
        if (filter.getSortingOrderList() == null){
            filter.setSortingOrderList(new ArrayList<>());
        }
        if (filter.getDirection() != null){
            filter.getSortingOrderList().add(new Sort.Order(filter.getDirection(), filter.getOrderBy()));
        }

        return PageRequest.of(filter.getPage(), filter.getPageSize(), Sort.by(filter.getSortingOrderList()));
    }
}
