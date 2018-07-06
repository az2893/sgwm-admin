package com.abs.controller;

import com.abs.bean.User;
import com.abs.constant.PageCodeEnum;
import com.abs.constant.SessionKeyConst;
import com.abs.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private HttpSession session;
    /**
     * 登录页面
     */

    @RequestMapping
    public String loginIndex(){
        return  "/system/login";
    }
    /**
     * 验证用户名密码是否正确
     * 正确进入主页
     * 错误返回登陆页
     * */
    @RequestMapping(value =("validate"),method = RequestMethod.POST)
    public  String validata(User user, RedirectAttributes attr){
        if(user!=null && user.getName()!=null && user.getPassword()!=null){
            //检测用户名密码是否正确
            if(userServiceI.login(user)){
                session.setAttribute(SessionKeyConst.USER_INFO,user);
                //1.根据用户组id获得菜单
                //user.getGroupId()

                //设置权限 未完成
                return "redirect:/index";
            }else{
                attr.addFlashAttribute(PageCodeEnum.KEY, PageCodeEnum.LOGIN_FAIL);
                return "redirect:/login";
            }
        }
        else{
            attr.addFlashAttribute(PageCodeEnum.KEY, PageCodeEnum.LOGIN_FAIL);
            return "redirect:/login";
        }
}


}
