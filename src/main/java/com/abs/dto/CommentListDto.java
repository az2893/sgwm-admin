package com.abs.dto;


import java.util.List;

public class CommentListDto {
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<CommentDto> getData() {
        return data;
    }

    public void setData(List<CommentDto> data) {
        this.data = data;
    }

    private  boolean hasMore;
    private List<CommentDto> data;
}
