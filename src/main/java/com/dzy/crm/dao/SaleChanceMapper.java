package com.dzy.crm.dao;

import com.dzy.crm.base.BaseMapper;
import com.dzy.crm.vo.SaleChance;

public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {
    Integer deleteByPrimaryKey(Integer id);

    int insert(SaleChance record);

    Integer insertSelective(SaleChance record);

    SaleChance selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(SaleChance record);

    int updateByPrimaryKey(SaleChance record);
}