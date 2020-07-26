package com.craftsoft.callDetailRecord.details.page;

import java.util.List;

public class PagedListWithDetails<T> {
    private List<T> list;
    private int pageSize;
    private long pageCount;
    private long totalElements;

    protected PagedListWithDetails() {
    }

    public PagedListWithDetails(List<T> list, int pageSize, long totalElements) {
        if (pageSize <= 0) {
            pageSize = 1;
        }

        int totalPages = (int)(totalElements / (long)pageSize);
        if (totalElements % (long)pageSize != 0L) {
            ++totalPages;
        }

        this.list = list;
        this.pageSize = pageSize;
        this.pageCount = (long)totalPages;
        this.totalElements = totalElements;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
