package com.abs.service;

import com.abs.bean.Dic;
import com.abs.dao.DicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuJia on 2018/7/1.
 */
@Service
public class DicServiceImpl implements   DicServiceI {
    @Autowired
    private DicDao dicDao;
    @Override
    public List<Dic> getDicList(String type) {
        Dic dic= new Dic();
        dic.setType(type);
        return dicDao.select(dic);
    }
}
