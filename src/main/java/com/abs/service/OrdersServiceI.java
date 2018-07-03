package com.abs.service;

import com.abs.dto.ApiCodeDto;
import com.abs.dto.OrderForBuyDto;
import com.abs.dto.OrdersDto;

import java.util.List;

public interface OrdersServiceI {

      List<OrdersDto> getOrderlistBuUserId(OrdersDto ordersDto);

      OrdersDto getOrderById(long id);

      ApiCodeDto buy(OrderForBuyDto orderForBuyDto);


}
