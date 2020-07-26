package com.craftsoft.callDetailRecord.utils.page;

import com.craftsoft.callDetailRecord.details.page.PagedListWithDetails;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtils {
    public PageUtils() {
    }

    public static <T, R> PagedListWithDetails<R> newPagedListWithDetails(Page<T> page, Function<T, R> newDetailsFunction) {
        return newPagedListWithDetails(page, (List)page.getContent().stream().map(newDetailsFunction).collect(Collectors.toList()));
    }
    public static <T> PagedListWithDetails<T> newPagedListWithDetails(Page page, List<T> list) {
        list = (List) ObjectUtils.defaultIfNull(list, new ArrayList());
        return new PagedListWithDetails(list, page.getSize(), page.getTotalElements());
    }
}
