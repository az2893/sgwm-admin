package com.abs.service;

import com.abs.bean.Dic;

import java.util.List;

/**
 * Created by LiuJia on 2018/7/1.
 */
public interface DicServiceI {

    List<Dic> getDicList(String type);
}
