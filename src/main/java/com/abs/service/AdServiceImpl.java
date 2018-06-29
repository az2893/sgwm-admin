package com.abs.service;

import com.abs.bean.Ad;
import com.abs.dao.AdDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements  AdServiceI {

    @Autowired
    private AdDao adDao;

    @Override
    public List<Ad> getadlist(Ad ad) {
        return adDao.selectByPage(ad);
    }
}
