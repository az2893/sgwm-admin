package com.abs.dao;



import com.abs.bean.Member;



public interface MemberDao {
    /**
     * 根据查询条件查询会员列表
     * @param member 查询条件
     * @return 会员列表
     */
    Member select(Member member);
}