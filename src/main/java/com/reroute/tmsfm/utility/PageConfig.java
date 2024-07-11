package com.reroute.tmsfm.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageConfig extends PageRequest {

    public PageConfig(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }
}
