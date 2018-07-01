package com.abs.service;

import com.abs.bean.Ad;
import com.abs.dto.AdDto;

import java.util.List;

public interface AdServiceI {
    List<Ad> search(AdDto adDto);

    Ad getAdByid(long id);

    int add(AdDto adDto);

    int delete(long id);

    int modify(AdDto adDto);
}
