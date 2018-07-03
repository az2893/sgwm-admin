package com.abs.controller;

import com.abs.Util.CommonUtil;
import com.abs.bean.Business;
import com.abs.bean.Comment;
import com.abs.bean.Orders;
import com.abs.dto.*;
import com.abs.service.*;
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
    @Autowired
    private OrdersServiceI ordersServiceI;
    @Autowired
    private CommentServiceI commentServiceI;

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
        if(result.getErrno()==1)
            return result;
        //2.发送验证码
        String code=CommonUtil.getCode(6);
        if(memberServiceI.saveCode(phone,code)){
            //保存成功
            result= memberServiceI.sendCode(phone,code);
            if(result.getErrno()==0){
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
            result.setErrno(2);
            result.setMsg("您点击太快了，请稍等一下吧");
            return result;
        }
    }

    /**
     * 用户订单列表
     * @id 用户ID
     * */
    @RequestMapping(value = "/orderlist/{id}",method = RequestMethod.GET)
    @ResponseBody
    public List<OrdersDto> getListByUserId(@PathVariable("id")long id){
        OrdersDto ordersDto= new OrdersDto();
        ordersDto.setBusinessId(id);
        return ordersServiceI.getOrderlistBuUserId(ordersDto);
    }
    /**
     *购买
     * @
     * */
    @RequestMapping(value = "/order")
    @ResponseBody
    public ApiCodeDto buy(OrderForBuyDto orderForBuyDto){
        ApiCodeDto apiCodeDto;
        //1.判断是否登录
        if(orderForBuyDto.getToken()!=null){
            String token=orderForBuyDto.getToken();
            //检查token
            long phone=memberServiceI.getPhone(token);
            if(phone!=orderForBuyDto.getUsername()){
                apiCodeDto= new ApiCodeDto();
                apiCodeDto.setErrno(2);
                apiCodeDto.setMsg("登录失败，请重新登录");
            }
            //登录成功
            //检查价格，防止用户修改
            BusinessDto businessDto=businessServiceI.getBusinessById(orderForBuyDto.getId());
            orderForBuyDto.setPrice(businessDto.getPrice());
           apiCodeDto= ordersServiceI.buy(orderForBuyDto);
           return apiCodeDto;
        }else{
            apiCodeDto= new ApiCodeDto();
            apiCodeDto.setErrno(1);
            apiCodeDto.setMsg("请先登录");
            return apiCodeDto;
        }
    }
    /**
     * 评论－列表
     * */
    @RequestMapping(value="detail/comment/{pn}/{id}",method = RequestMethod.GET)
    @ResponseBody
    public CommentListDto getCommentDetailList(@PathVariable("pn")int pn,@PathVariable("id") long id){
        pn++;
        PageHelper.startPage(pn,7);
        List<CommentDto> list=commentServiceI.getCommenList(id);
        PageInfo<CommentDto> info=new PageInfo<CommentDto>(list);
        CommentListDto result= new CommentListDto();
        result.setHasMore(info.isHasNextPage());
        result.setData(list);
        return result;
    }
    /**
     * 提交评论
     * */
    @RequestMapping(value = "/submitComment", method = RequestMethod.POST)
    public ApiCodeDto submitComment(){
        return  null;
    }




}
