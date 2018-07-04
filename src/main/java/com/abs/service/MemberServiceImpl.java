package com.abs.service;

import com.abs.Util.CommonUtil;
import com.abs.bean.Member;
import com.abs.cache.CodeCache;
import com.abs.cache.TokenCache;
import com.abs.constant.ApiCodeEnum;
import com.abs.dao.MemberDao;
import com.abs.dto.ApiCodeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class MemberServiceImpl implements MemberServiceI {

    public  final static Logger logger = LoggerFactory.getLogger(MemberServiceI.class);
    @Autowired
    private MemberDao memberDao;
    @Override
    public ApiCodeDto checkPhone(long phone) {
        ApiCodeDto result= new ApiCodeDto();
        Member member= new Member();
        member.setPhone(phone);
        Member m=memberDao.select(member);
        if(m==null){
            result.setErrno(ApiCodeEnum.USERNAME_ERROR.getCode());
            result.setMsg(ApiCodeEnum.USERNAME_ERROR.getMsg());
            return  result;
        }
        //用户名正确
        result.setErrno(ApiCodeEnum.USERNAME_TRUE.getCode());
        result.setMsg(ApiCodeEnum.USERNAME_TRUE.getMsg());
        return  result;
    }

    @Override
    public ApiCodeDto sendCode(long phone, String code) {
        ApiCodeDto result= new ApiCodeDto();
        result.setErrno(ApiCodeEnum.CODE_SEND_SUCCESS.getCode());
        result.setMsg(code);
        logger.info(phone+"|"+code);
        return result;
    }


    @Override
    public ApiCodeDto login(long phone, String code) {
        //检查手机号是否有效
        ApiCodeDto apiCodeDto=this.checkPhone(phone);
        if(apiCodeDto.getErrno()==ApiCodeEnum.USERNAME_ERROR.getCode())
            return apiCodeDto;
        CodeCache instance=CodeCache.getInstance();
        String realCode=instance.getCode(phone);
        ApiCodeDto result= new ApiCodeDto();
        if(realCode.equals(code)){
            result.setErrno(ApiCodeEnum.LOGIN_SUCCESS.getCode());
            result.setMsg(ApiCodeEnum.LOGIN_SUCCESS.getMsg());
            //生成token
            String token=CommonUtil.getUUID();
            //保存token
            TokenCache.getInstance().save(token,phone);
            result.setToken(token);
        }
        else{
            result.setErrno(ApiCodeEnum.LOGIN_FAIL.getCode());
            result.setMsg(ApiCodeEnum.LOGIN_FAIL.getMsg());
        }
        return result;
    }

    @Override
    public boolean saveCode(Long phone, String code) {
        //需要借助第三方提供短信借口 简化随机生成六位数
        CodeCache instance=CodeCache.getInstance();
        String tmepcode= DigestUtils.md5DigestAsHex(code.getBytes());
        return instance.save(phone,tmepcode);
    }
    @Override
    public long getPhone(String token){
        return TokenCache.getInstance().getPhone(token);
    }

    @Override
    public Member getMemberByparam(Member member) {
        return memberDao.select(member);
    }
}
