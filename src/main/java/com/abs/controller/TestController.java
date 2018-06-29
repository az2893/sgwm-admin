package com.abs.controller;

import com.abs.bean.Ad;
import com.abs.service.AdServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/ad")
public class TestController {
    @Autowired
    private AdServiceI adServiceI;
    @RequestMapping("/test")
    public String test(){
        System.out.println("111");
        return "/test";
    }
    @RequestMapping("/add")
    public String add(Ad ad){
        //
        return null;
    }

    @RequestMapping("/adlist")
    @ResponseBody
    public List<Ad> getAdList(Ad ad){
        return adServiceI.getadlist(ad);
    }

}
