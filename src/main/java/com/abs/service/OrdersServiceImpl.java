package com.abs.service;

import com.abs.bean.Member;
import com.abs.bean.Orders;
import com.abs.dao.OrdersDao;
import com.abs.dto.ApiCodeDto;
import com.abs.dto.OrderForBuyDto;
import com.abs.dto.OrdersDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrdersServiceImpl implements   OrdersServiceI {
    @Value("${businessImage.url}")
    private String imgUrl;
    @Autowired
    private OrdersDao ordersDao;
    @Override
    public List<OrdersDto> getOrderlistBuUserId(OrdersDto ordersDto) {
        Orders o= new Orders();
        BeanUtils.copyProperties(ordersDto,o);
        List<Orders> orderslist= ordersDao.select(o);
        List<OrdersDto> result= new ArrayList<OrdersDto>();
        for (Orders orders:orderslist) {
            OrdersDto ordersDto1= new OrdersDto();
            BeanUtils.copyProperties(orders,ordersDto1);
            result.add(ordersDto1);
            ordersDto1.setCount(orders.getNum());
            ordersDto1.setTitle(orders.getBusiness().getTitle());
            ordersDto1.setImg(imgUrl+orders.getBusiness().getImgFileName());
        }
        return result;
    }

    @Override
    public OrdersDto getOrderById(long id) {
        return null;
    }

    @Override
    public ApiCodeDto buy(OrderForBuyDto orderForBuyDto) {
        Orders orders= new Orders();
        orders.setNum(orderForBuyDto.getNum());
        //价格有问题。。

        orders.setPrice(orderForBuyDto.getPrice());
        orders.setBusinessId(orderForBuyDto.getId());
        orders.setCommentState(0);//为评论＝0 评论＝2；
        int code=ordersDao.insert(orders);
        ApiCodeDto apiCodeDto= new ApiCodeDto();
        if(code==1){
            apiCodeDto.setErrno(0);
            apiCodeDto.setMsg("购买成功");
        }else{
            apiCodeDto.setErrno(1);
            apiCodeDto.setMsg("购买失败 请重试");
        }


        return apiCodeDto;
    }
}
