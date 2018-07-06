package com.abs.service;

import com.abs.bean.User;
import com.abs.dao.UserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private UserDao userDao;
    @Override
    public boolean login(User u) {
        List<User> userList=userDao.select(u);
        if(userList.size() == 1) {
            BeanUtils.copyProperties(userList.get(0),u);
            return true;
        }
        return false;
    }
}
