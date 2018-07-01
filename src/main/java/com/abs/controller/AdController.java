package com.abs.controller;

import com.abs.bean.Ad;
import com.abs.constant.PageCodeEnum;
import com.abs.constant.Result;
import com.abs.dto.AdDto;
import com.abs.service.AdServiceI;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
@RequestMapping("/ad")
public class AdController {
    @Autowired
    private AdServiceI adServiceI;

    /**
     * 初始化广告新增页面
     * */
    @RequestMapping("/addInit")
    public String addinit(){
        return "/content/adAdd";
    }

    /**
     * 添加新广告信息
     * */
    @RequestMapping("/add")
    public String add(AdDto adDto, ModelMap model){

        int code=adServiceI.add(adDto);
        model.addAttribute(PageCodeEnum.KEY,PageCodeEnum.ADD_SUCCESS);
        return "/content/adAdd";
    }
    /**
     * 初始化
     * */
    @RequestMapping("/adlist/{pn}")
    public String init(@PathVariable("pn") Integer pn, ModelMap model){
        if(pn==null || pn==0)
            pn=1;
        AdDto adDto= new AdDto();
        PageHelper.startPage(pn, 6);
        List<Ad> adlist=adServiceI.search(adDto);
        PageInfo<Ad> p = new PageInfo<Ad>(adlist);
        model.addAttribute("pageinfo",p);
        model.addAttribute("list",adlist);
        return "/content/adList";
    }
    @RequestMapping("/getadlist/{pn}")
    @ResponseBody
    public List<Ad> getAdList(@PathVariable("pn") Integer pn, ModelMap model){
        if(pn==null || pn==0)
            pn=1;
        AdDto adDto= new AdDto();
        PageHelper.startPage(pn, 6);
        List<Ad> adlist=adServiceI.search(adDto);
        PageInfo<Ad> p = new PageInfo<Ad>(adlist);
        model.addAttribute("pageinfo",p);
        return adlist;
    }
    /**
     * 按条件查找 name为空表示无条件
     * @name
     * */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ResponseBody
    public List<Ad> search(@RequestParam("name") String name,ModelMap model){
        AdDto adDto = new AdDto();
        if((name.trim()!="" ||name !=null)) {
            try {
                name = new String(name.getBytes("ISO8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            adDto.setTitle(name);
        }
        List<Ad> adlist=adServiceI.search(adDto);
        return adlist;
    }

    /**
     * 删除
     * @id
     * */
    @RequestMapping("delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") long id){
       int code= adServiceI.delete(id);
        return  new Result(code,"删除成功");
    }

    /**
     * 修改初始化
     */
    @RequestMapping("/modifyInit")
    public String modifyInit(ModelMap model, @RequestParam("id") Long id){
        model.addAttribute("modifyObj",adServiceI.getAdByid(id));
        return  "/content/adModify";
    }

    /**
     * 修改
     * */
    @RequestMapping("/modify")
    public String modify(AdDto adDto){
        adServiceI.modify(adDto);
        return  "redirect:/ad/adlist/1";
    }




}
