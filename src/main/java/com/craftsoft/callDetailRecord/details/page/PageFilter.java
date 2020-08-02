package com.craftsoft.callDetailRecord.details.page;

import org.springframework.data.domain.Sort;

import java.util.List;

public class PageFilter {
    private Integer page;
    private Integer pageSize;
    private String orderBy;
    private Sort.Direction direction;
    private List<Sort.Order> sortingOrderList;

    protected PageFilter() {
        setPage(0);
        setPageSize(10);
    }

    public PageFilter(Integer page, Integer pageSize, String orderBy, Sort.Direction direction, List<Sort.Order> sortingOrderList) {
        if (page < 0) {
            setPage(0);
        }
        if (pageSize <= 0) {
            setPageSize(10);
        }

        this.page = page;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.direction = direction;
        this.sortingOrderList = sortingOrderList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public List<Sort.Order> getSortingOrderList() {
        return sortingOrderList;
    }

    public void setSortingOrderList(List<Sort.Order> sortingOrderList) {
        this.sortingOrderList = sortingOrderList;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

