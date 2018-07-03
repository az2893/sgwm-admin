package com.abs.service;

import com.abs.bean.Business;
import com.abs.bean.Comment;
import com.abs.bean.Orders;
import com.abs.dao.BusinessDao;
import com.abs.dao.CommentDao;
import com.abs.dao.OrdersDao;
import com.abs.dto.CommentDto;
import com.abs.dto.CommentListDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentServiceI{
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private BusinessDao businessDao;
    @Override
    public List<CommentDto> getCommenList(Long id) {
        Comment comment= new Comment();
        Business business= new Business();
        Orders orders= new Orders();
        business.setId(id);
        business=businessDao.selectById(id);
        comment.setOrders(orders);
        orders.setBusiness(business);
        orders.setBusinessId(id);
        List<Comment> commentList=commentDao.selectByPage(comment);
        List<CommentDto> list= new ArrayList<CommentDto>();
        for (Comment c:commentList) {
            CommentDto commentDto= new CommentDto();
            list.add(commentDto);
            BeanUtils.copyProperties(comment,commentDto);
            String usernameTemp=String.valueOf(comment.getOrders().getMember().getPhone());
            StringBuffer sb= new StringBuffer(usernameTemp);
            sb.replace(3,9,"*******");
            commentDto.setUsername(sb.toString());
            list.add(commentDto);
        }
        return list;
    }

    @Override
    public int addComment(Comment comment) {
        return 1;
    }
}
