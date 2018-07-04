package com.abs.controller;

import com.abs.Util.CommonUtil;
import com.abs.bean.Business;
import com.abs.bean.Comment;
import com.abs.bean.Member;
import com.abs.bean.Orders;
import com.abs.constant.ApiCodeEnum;
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
        List<BusinessDto> businessList=businessServiceI.getListByparam(business);
        PageInfo<BusinessDto> pageInfo= new PageInfo<BusinessDto>(businessList);
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
        List<BusinessDto> businessList=businessServiceI.getListByparam(business);
        PageInfo<BusinessDto> pageInfo= new PageInfo<BusinessDto>(businessList);
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
        List<BusinessDto> businessList=businessServiceI.getListByparam(business);
        PageInfo<BusinessDto> pageInfo= new PageInfo<BusinessDto>(businessList);
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
       //result.setErrno();
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
        if(result.getErrno()==ApiCodeEnum.USERNAME_ERROR.getCode())
            return result;
        //2.发送验证码 6位
        String code=CommonUtil.getCode(6);
        if(memberServiceI.saveCode(phone,code)){
            //保存成功
            result= memberServiceI.sendCode(phone,code);

        }else{
            result.setErrno(ApiCodeEnum.CLICKQUICK_FAIL.getCode());
            result.setMsg(ApiCodeEnum.CLICKQUICK_FAIL.getMsg());
        }
        return result;
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
                apiCodeDto.setErrno(ApiCodeEnum.LOGIN_FAIL.getCode());
                apiCodeDto.setMsg(ApiCodeEnum.LOGIN_FAIL.getMsg());
            }
            //登录成功
            //检查价格，防止用户修改
            BusinessDto businessDto=businessServiceI.getBusinessById(orderForBuyDto.getId());
            orderForBuyDto.setPrice(businessDto.getPrice());
            //购买
           apiCodeDto= ordersServiceI.buy(orderForBuyDto);

           //更新商品销量
            businessServiceI.updateNumber(orderForBuyDto.getId());

           return apiCodeDto;
        }else{
            apiCodeDto= new ApiCodeDto();
            apiCodeDto.setErrno(ApiCodeEnum.NOT_LOGIN.getCode());
            apiCodeDto.setMsg(ApiCodeEnum.NOT_LOGIN.getMsg());
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
    @ResponseBody
    public ApiCodeDto submitComment(CommentForSubmitDto commentForSubmitDto){
        ApiCodeDto apiCodeDto;
        //1.验证token
        long usernameTemp=memberServiceI.getPhone(commentForSubmitDto.getToken());
        if(!(usernameTemp==commentForSubmitDto.getUsername()))
        {
            apiCodeDto= new ApiCodeDto();
            apiCodeDto.setErrno(ApiCodeEnum.NOT_LOGIN.getCode());
            apiCodeDto.setMsg(ApiCodeEnum.NOT_LOGIN.getMsg());
            return apiCodeDto;
        }
        //2.校验订单id和用户一致
        Member memberTemp= new Member();
        memberTemp.setPhone(usernameTemp);
        Member m=memberServiceI.getMemberByparam(memberTemp);
        Orders orders=ordersServiceI.getOrderById(commentForSubmitDto.getId());
        if(orders.getMemberId()==m.getId()){
            //开始评论
            Comment comment= new Comment();
            comment.setComment(commentForSubmitDto.getComment());
            comment.setStar(commentForSubmitDto.getStar());
            comment.setOrdersId(commentForSubmitDto.getId());
            int code=commentServiceI.addComment(comment);
            if(code==1){
                apiCodeDto= new ApiCodeDto();
                apiCodeDto.setErrno(0);
                apiCodeDto.setMsg("评论成功");
                //修改订单表 评论状态
                orders.setCommentState(2);
                ordersServiceI.updateCommentStatus(orders);
                //更新商户星级
                ordersServiceI.updateBusinessStarNumber(orders.getBusinessId());
                //更新评论数量
                ordersServiceI.updateBusinessCommentNumbers(orders.getBusinessId());

            }else{
                apiCodeDto= new ApiCodeDto();
                apiCodeDto.setErrno(ApiCodeEnum.COMMENT_FAIL.getCode());
                apiCodeDto.setMsg(ApiCodeEnum.COMMENT_FAIL.getMsg());
            }
            return apiCodeDto;
        }
        apiCodeDto= new ApiCodeDto();
        apiCodeDto.setErrno(ApiCodeEnum.COMMENT_FAIL.getCode());
        apiCodeDto.setMsg(ApiCodeEnum.COMMENT_FAIL.getMsg());
        return  apiCodeDto;
    }

}
