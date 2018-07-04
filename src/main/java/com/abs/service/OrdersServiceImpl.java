package com.abs.service;

import com.abs.bean.Member;
import com.abs.bean.Orders;
import com.abs.constant.ApiCodeEnum;
import com.abs.dao.BusinessDao;
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
    @Autowired
    private MemberServiceI memberServiceI;
    @Autowired
    private BusinessDao businessDao;
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
        Orders orders=ordersDao.selectById(id);
        OrdersDto ordersDto= new OrdersDto();
        BeanUtils.copyProperties(orders,ordersDto);
        //ordersDto.set
        return ordersDto;
    }

    @Override
    public ApiCodeDto buy(OrderForBuyDto orderForBuyDto) {
        Orders orders= new Orders();
        orders.setNum(orderForBuyDto.getNum());
        //检查价格
        orders.setPrice(orderForBuyDto.getPrice());
        orders.setBusinessId(orderForBuyDto.getId());
        orders.setCommentState(0);//为评论＝0 评论＝2；
        Member memberTemp= new Member();
        memberTemp.setPhone(orderForBuyDto.getUsername());
        Member m=memberServiceI.getMemberByparam(memberTemp);
        orders.setMemberId(m.getId());
        int code=ordersDao.insert(orders);
        ApiCodeDto apiCodeDto= new ApiCodeDto();
        if(code==1){
            apiCodeDto.setErrno(ApiCodeEnum.BUY_SUCCESS.getCode());
            apiCodeDto.setMsg(ApiCodeEnum.BUY_SUCCESS.getMsg());
        }else{
            apiCodeDto.setErrno(ApiCodeEnum.BUY_FAIL.getCode());
            apiCodeDto.setMsg(ApiCodeEnum.BUY_FAIL.getMsg());
        }
        return apiCodeDto;
    }

    @Override
    public int updateCommentStatus(Orders orders) {

        return ordersDao.update(orders);
    }

    @Override
    public int updateBusinessStarNumber(long id) {
        return businessDao.updateStar(id);
    }

    @Override
    public int updateBusinessCommentNumbers(long id) {
        return businessDao.updateCommentNumbers(id);
    }
}
