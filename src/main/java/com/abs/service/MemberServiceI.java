package com.abs.service;

import com.abs.bean.Member;
import com.abs.dto.ApiCodeDto;

public interface MemberServiceI {
    ApiCodeDto checkPhone(long phone);

    ApiCodeDto sendCode(long phone,String code);

    ApiCodeDto login(long phone,String code);

    boolean saveCode(Long phone, String code);
    public long getPhone(String token);

    Member getMemberByparam(Member member);

}
