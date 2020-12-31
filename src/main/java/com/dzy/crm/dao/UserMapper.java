package com.dzy.crm.dao;

import com.dzy.crm.base.BaseMapper;

import com.dzy.crm.vo.User;


import java.util.List;
import java.util.Map;


/**
 * @author 董志远
 */
public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserByUserName(String userName);

    List<Map<String,Object>> queryAllSales();
}