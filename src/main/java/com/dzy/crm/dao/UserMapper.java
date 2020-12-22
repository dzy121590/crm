package com.dzy.crm.dao;

import com.dzy.crm.base.BaseMapper;
import com.dzy.crm.base.BaseService;
import com.dzy.crm.vo.User;
import org.springframework.stereotype.Repository;


/**
 * @author 董志远
 */
public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserByUserName(String userName);

}