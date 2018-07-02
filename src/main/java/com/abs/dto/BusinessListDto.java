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

    public List<Business> getData() {
        return data;
    }

    public void setData(List<Business> data) {
        this.data = data;
    }

    private boolean hasMore;
    List<Business> data;
}
