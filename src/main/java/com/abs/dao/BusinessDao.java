package com.abs.dao;



import com.abs.bean.Business;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BusinessDao {
	
	/**
	 * 新增
	 * @param business 商户表对象
	 * @return 影响行数
	 */
	int insert(Business business);
    
    /**
     *  根据主键查询商户
     * @param id 主键
     * @return 商户对象
     */
    Business selectById(Long id);
    
    /**
     *  根据查询条件分页查询商户列表
     * @param business 查询条件
     * @return 商户列表
     */
    List<Business> selectAll(Business business);
    
    /**
     *  根据查询条件分页查询商户列表 : 
     *  标题、副标题、描述三个过滤条件为模糊查询
     *  并且这三个过滤条件之间为或者的关系，用 OR 连接
     *  这三个过滤条件与其他过滤条件依然是并且关系，用 AND 连接
     * @param business 查询条件
     * @return 商户列表
     */
    List<Business> selectLikeByPage(Business business);
    
    /**
     * 更新商户的【统计评论星星总数】
     *
     * @return
     */
    int updateStar(Long id);
    /**
     * 更新商户 评论数量
     * @id
     * */
    int updateCommentNumbers(Long id);

    /**
     * 更新商户的信息
     * */
    int updateBusiness(Business business);

    /**
     * 更新商户销量
     * */
    int updateNumber(Long id);

    /**
     * 删除商户
     * */
    int delete(long id);


}
