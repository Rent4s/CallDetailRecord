package com.craftsoft.callDetailRecord.repositories.impl;

import com.craftsoft.callDetailRecord.details.AverageCallRecordAttributeFilter;
import com.craftsoft.callDetailRecord.details.CallRecordFilter;
import com.craftsoft.callDetailRecord.entity.CallRecord;
import com.craftsoft.callDetailRecord.entity.CallRecord_;
import com.craftsoft.callDetailRecord.repositories.CallRecordRepositoryCustom;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CallRecordRepositoryImpl implements CallRecordRepositoryCustom {

    @Autowired
    EntityManager entityManager;


    @Override
    public Page<CallRecord> list(CallRecordFilter filter) {

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(CallRecord.class);

        var root = criteriaQuery.from(CallRecord.class);

        var predicateArray = getPredicates(filter, criteriaBuilder, root);
        criteriaQuery.where(predicateArray);
        criteriaQuery.distinct(true);

        var pageable = getPageRequest(filter);
        prepareOrder(filter, criteriaQuery, root);

        var typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        var results = typedQuery.getResultList();
        var totalElements = getTotalElements(criteriaBuilder, predicateArray);

        return new PageImpl<>(results, pageable, totalElements);
    }

    private Predicate[] getPredicates(CallRecordFilter filter, CriteriaBuilder criteriaBuilder, Root<CallRecord> root) {
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
        var datePredicateList = getDatePredicates(filter.getStartDateFrom(), filter.getStartDateTo(), filter.getEndDateFrom(), filter.getEndDateTo(), criteriaBuilder, root);

        if (CollectionUtils.isNotEmpty(datePredicateList)) {
            predicateList.addAll(datePredicateList);
        }

        return predicateList.toArray(new Predicate[]{});
    }

    private void prepareOrder(CallRecordFilter filter, CriteriaQuery<CallRecord> criteriaQuery, Root<CallRecord> root) {
        List<Order> sortingOrder  = filter.getSortingOrderList().stream().map(x-> new OrderImpl(root.get(x.getProperty()), x.isAscending())).collect(Collectors.toList());
        criteriaQuery.orderBy(sortingOrder);
    }

    @Override
    public Double averageCallCost(AverageCallRecordAttributeFilter filter) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var averageCriteriaQuery = criteriaBuilder.createQuery(Double.class);
        var root = averageCriteriaQuery.from(CallRecord.class);

        var predicateList = getDatePredicates(filter.getStartDateFrom(), filter.getStartDateTo(), filter.getEndDateFrom(), filter.getEndDateTo(), criteriaBuilder, root);

        averageCriteriaQuery.where(predicateList.toArray(new Predicate[]{}));
        averageCriteriaQuery.select(criteriaBuilder.avg(root.get(CallRecord_.costPerMinute)));
        TypedQuery<Double> typedQuery = entityManager.createQuery(averageCriteriaQuery);

        return typedQuery.getSingleResult();
    }

    private List<Predicate> getDatePredicates(Timestamp startDateFrom, Timestamp startDateTo, Timestamp endDateFrom, Timestamp endDateTo, CriteriaBuilder criteriaBuilder, Root<CallRecord> root) {
        var predicateList = new ArrayList<Predicate>();
        if (startDateFrom != null){
            var predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(CallRecord_.startDate), startDateFrom);
            predicateList.add(predicate);
        }
        if (startDateTo != null){
            var predicate = criteriaBuilder.lessThanOrEqualTo(root.get(CallRecord_.startDate), startDateTo);
            predicateList.add(predicate);
        }
        if (endDateFrom != null){
            var predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(CallRecord_.endDate), endDateFrom);
            predicateList.add(predicate);
        }
        if (endDateTo != null){
            var predicate = criteriaBuilder.lessThanOrEqualTo(root.get(CallRecord_.endDate), endDateTo);
            predicateList.add(predicate);
        }
        return predicateList;
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
        var page = filter.getPage();
        page--;
        return PageRequest.of(page, filter.getPageSize(), Sort.by(filter.getSortingOrderList()));
    }
}
