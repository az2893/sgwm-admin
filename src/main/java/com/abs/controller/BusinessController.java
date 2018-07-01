package com.abs.controller;

import com.abs.bean.Business;
import com.abs.constant.Result;
import com.abs.dto.BusinessDto;
import com.abs.service.BusinessServiceI;
import com.abs.service.DicServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by LiuJia on 2018/7/1.
 */
@Controller
@RequestMapping("/businesses")
public class BusinessController {
    @Autowired
    private BusinessServiceI businessServiceI;
    @Autowired
    private DicServiceI dicServiceI;
    /**
     * 初始化列表页面
     */

    @RequestMapping("/init")
    public  String init(ModelMap model){
        model.addAttribute("list",businessServiceI.getAllBusinessList());
        return "/content/businessList";
    }

    /**
     * 查询
     * 根据title
     * */
    @RequestMapping(method = RequestMethod.GET)
    public String search(ModelMap model,Business business){
        System.out.print("111111111111");
        model.addAttribute("list",businessServiceI.getListByparam(business));
        return "/content/businessList";
    }

     /**
      * 初始化新增
      * */
     @RequestMapping(value = "/addPage",method = RequestMethod.GET)
     public String initadd(ModelMap model){
         model.addAttribute("cityList",dicServiceI.getDicList("city"));
         model.addAttribute("categoryList",dicServiceI.getDicList("category"));
        return "/content/businessAdd";
     }
     /**
      * 新增
      * */
     @RequestMapping(method = RequestMethod.POST)
    public String add(BusinessDto businessDto,ModelMap model){
         System.out.println("11111111111111111111111111111111111111111111111111111111111111");
         int code=businessServiceI.add(businessDto);
         System.out.println(code);
        model.addAttribute("pageCode",new Result(1001,"添加成功"));
         return "/content/businessAdd";
     }

     /**
      *删除
      * @id
      * */
     @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") long id,ModelMap model){
        int code= businessServiceI.delete(id);
        //model.addAttribute("")
        return  "redirect:/businesses/init";
     }
    /**
     * 更新初始化页面
     * @id
     * */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String modifyInit(@PathVariable("id")long id,ModelMap model){
        Business business=businessServiceI.getBusinessById(id);
        model.addAttribute("modifyObj",business);
        model.addAttribute("cityList",dicServiceI.getDicList("city"));
        model.addAttribute("categoryList",dicServiceI.getDicList("category"));
        return "/content/businessModify";
    }
    /**
     * 更新
     * */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public String motify(@PathVariable("id") Long id, BusinessDto dto){
       int code= businessServiceI.modify(dto);
       return  "redirect:/businesses/init";
    }



}
