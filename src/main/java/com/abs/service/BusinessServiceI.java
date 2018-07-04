package com.abs.service;

import com.abs.bean.Business;
import com.abs.dto.BusinessDto;

import java.util.List;

/**
 * Created by LiuJia on 2018/7/1.
 */
public interface BusinessServiceI {
    /**
     * 查所有
     * */
    List<Business> getAllBusinessList();

    /**
     * 查单个
     * */
    BusinessDto getBusinessById(long id);

    /**
     *根据条件查找
     * */
    List<BusinessDto> getListByparam(Business business);

    /**
     *  add
     * */
    int add(BusinessDto businessDto);

    int delete(long id);

    int modify(BusinessDto businessDto);

    int updateNumber(long id);


}
