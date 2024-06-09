package com.reroute.tmsfm.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageConfig extends PageRequest {

    public PageConfig(int pageNumber, int pageSize, Sort sort) {
        super(getFirstPage(pageNumber, pageSize), pageSize, sort);
    }

    private static int getFirstPage(int pageNumber, int pageSize) {
        return pageNumber != 0 ? pageNumber / pageSize : 0;
    }
}
