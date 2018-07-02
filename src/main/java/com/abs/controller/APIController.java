package com.abs.controller;

import com.abs.Util.CommonUtil;
import com.abs.bean.Business;
import com.abs.dto.AdDto;
import com.abs.dto.ApiCodeDto;
import com.abs.dto.BusinessDto;
import com.abs.dto.BusinessListDto;
import com.abs.service.AdServiceI;
import com.abs.service.BusinessServiceI;
import com.abs.service.MemberServiceI;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class APIController {
    @Autowired
    private AdServiceI adServiceI;
    @Autowired
    private BusinessServiceI businessServiceI;
    @Autowired
    private MemberServiceI memberServiceI;

    /**
     * 广告列表
     *
     * */
    @RequestMapping(value = "/homead",method = RequestMethod.GET)
    @ResponseBody
    public List<AdDto> homead(){
        int pn=1;
        AdDto adDto= new AdDto();
        PageHelper.startPage(pn, 6);
        List<AdDto> adlist=adServiceI.search(adDto);
        PageInfo<AdDto> p = new PageInfo<AdDto>(adlist);
        return adlist;
    }
    /**
     * 商户详情
     * @id
     * */
    @RequestMapping(value = "/detail/info/{id}",method = RequestMethod.GET)
    @ResponseBody
    public BusinessDto businessInfo(@PathVariable("id") long id){
        return businessServiceI.getBusinessById(id);
    }
    /**
     *商户列表
     * */
    @RequestMapping(value="/homelist/{city}/{pn}", method = RequestMethod.GET)
    @ResponseBody
    public BusinessListDto getList(Business business,@PathVariable("pn") int pn){
        pn++;
        PageHelper.startPage(pn,5);
        List<Business> businessList=businessServiceI.getListByparam(business);
        PageInfo<Business> pageInfo= new PageInfo<Business>(businessList);
        BusinessListDto result= new BusinessListDto();
        result.setHasMore(pageInfo.isHasNextPage());
        result.setData(businessList);
        return result;
    }
    /**
     * 商户列表－查询－两个参数－城市和分类
     * @city
     * @category
     * */
    @RequestMapping(value = "/search/{pn}/{city}/{category}",method = RequestMethod.GET)
    @ResponseBody
    public BusinessListDto searchBusiness(Business business,@PathVariable("pn") int pn){
        pn=pn+1;
        PageHelper.startPage(pn,5);
        List<Business> businessList=businessServiceI.getListByparam(business);
        PageInfo<Business> pageInfo= new PageInfo<Business>(businessList);
        BusinessListDto result= new BusinessListDto();
        result.setHasMore(pageInfo.isHasNextPage());
        result.setData(businessList);
        return result;
    }
    /**
     * 商户列表－查询－三个参数－城市：分类：关键字
     * @city
     * @category
     * */
    @RequestMapping(value = "/search/{pn}/{city}/{category}/{keyword}",method = RequestMethod.GET)
    @ResponseBody
    public BusinessListDto searchBusiness(Business business,@PathVariable("pn") int pn,@PathVariable("keyword") String keyword){
        business.setTitle(keyword);
        pn++;
        PageHelper.startPage(pn,5);
        List<Business> businessList=businessServiceI.getListByparam(business);
        PageInfo<Business> pageInfo= new PageInfo<Business>(businessList);
        BusinessListDto result= new BusinessListDto();
        result.setHasMore(pageInfo.isHasNextPage());
        result.setData(businessList);
        return result;
    }
    /**
     * 登录
     * @pnone
     * @code
     * */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ApiCodeDto login(@RequestParam("username") Long phone, String code){
       ApiCodeDto result= memberServiceI.login(phone,code);
        return result;
    }
    /**
     * 发送验证码
     *
     **/
    @RequestMapping(value = "/sms",method = RequestMethod.POST)
    @ResponseBody
    public ApiCodeDto sendCode(@RequestParam("username") Long phone){
        ApiCodeDto result;
        // 1.判断用户名是否存在
        result=memberServiceI.checkPhone(phone);
        if(result.getError()==1)
            return result;
        //2.发送验证码
        String code=CommonUtil.getCode(6);
        if(memberServiceI.saveCode(phone,code)){
            //保存成功
            result= memberServiceI.sendCode(phone,code);
            if(result.getError()==0){
                //发送成功
                result.setMsg(code);
                return  result;
            }
            else{
                //发送失败
                result.setMsg("发送失败，未知错误");
                return  result;
            }
        }else{
            result.setError(2);
            result.setMsg("您点击太快了，请稍等一下吧");
            return result;
        }
    }



}
