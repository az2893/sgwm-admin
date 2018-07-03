package com.abs.dto;

import com.abs.bean.Comment;

public class CommentDto extends Comment {


    /**
     * 四位隐藏的手机号
     * */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
