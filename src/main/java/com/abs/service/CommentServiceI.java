package com.abs.service;

import com.abs.bean.Comment;
import com.abs.dto.CommentDto;
import com.abs.dto.CommentListDto;

import java.util.List;

public interface CommentServiceI {
     /**
      * 查找评论列表
      * 条件查找
      * */
    List<CommentDto> getCommenList(Long id);

     /**
      * 新增评论
      * */
     int addComment(Comment comment);
}
