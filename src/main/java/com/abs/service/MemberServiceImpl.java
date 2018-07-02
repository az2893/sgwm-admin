package com.abs.service;

import com.abs.bean.Member;
import com.abs.cache.CodeCache;
import com.abs.dao.MemberDao;
import com.abs.dto.ApiCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class MemberServiceImpl implements MemberServiceI {
    @Autowired
    private MemberDao memberDao;
    @Override
    public ApiCodeDto checkPhone(long phone) {
        ApiCodeDto result= new ApiCodeDto();
        Member member= new Member();
        member.setPhone(phone);
        Member m=memberDao.select(member);
        if(m==null){
            result.setError(1);
            result.setMsg("用户名错误");
            return  result;
        }
        result.setError(0);
        result.setMsg("");
        return  result;
    }

    @Override
    public ApiCodeDto sendCode(long phone, String code) {
        ApiCodeDto result= new ApiCodeDto();
        result.setError(0);
        result.setMsg(code);

        return result;
    }


    @Override
    public ApiCodeDto login(long phone, String code) {
        ApiCodeDto apiCodeDto=this.checkPhone(phone);
        if(apiCodeDto.getError()==1)
            return apiCodeDto;
        CodeCache instance=CodeCache.getInstance();
        String realCode=instance.getCode(phone);
        ApiCodeDto result= new ApiCodeDto();
        if(realCode==code){
            result.setError(0);
            result.setMsg("登录成功");
        }
        else{
            result.setError(3);
            result.setMsg("登录失败，验证码不正确");
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
}
