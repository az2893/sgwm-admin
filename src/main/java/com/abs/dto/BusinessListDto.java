package com.abs.dto;

import com.abs.bean.Business;

import java.util.List;

public class BusinessListDto  {
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<BusinessDto> getData() {
        return data;
    }

    public void setData(List<BusinessDto> data) {
        this.data = data;
    }

    private boolean hasMore;
    List<BusinessDto> data;
}
