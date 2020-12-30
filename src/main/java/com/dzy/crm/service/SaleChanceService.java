package com.dzy.crm.service;

import com.dzy.crm.base.BaseService;
import com.dzy.crm.dao.SaleChanceMapper;
import com.dzy.crm.enums.DevResult;
import com.dzy.crm.enums.StateStatus;
import com.dzy.crm.query.SaleChanceQuery;
import com.dzy.crm.utils.AssertUtil;
import com.dzy.crm.utils.PhoneUtil;
import com.dzy.crm.vo.SaleChance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance, Integer> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    public Map<String, Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery) {

        Map<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getLimit());
        PageInfo<SaleChance> pageInfo = new PageInfo<SaleChance>(selectByParams(saleChanceQuery));

        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());

        return map;

    }

    public void saveSaleChance(SaleChance saleChance) {

        //校验
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        //设置默认
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());
        if (StringUtils.isNotBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(saleChance) < 1, "添加失败！");
    }

    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "请输入客户名！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "请输入联系人！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "请输入手机号！");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(linkPhone)), "请输入正确手机号！");

    }

    public void updateSaleChance(SaleChance saleChance) {
        //参数校验
        SaleChance temp = selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(null == temp, "更新用户不存在！");
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());

        if (StringUtils.isBlank(temp.getAssignMan()) && StringUtils.isNotBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        } else if (StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
            saleChance.setAssignMan("");
        }

        AssertUtil.isTrue(updateByPrimaryKeySelective(saleChance) < 1, "更新失败！");
    }

}
