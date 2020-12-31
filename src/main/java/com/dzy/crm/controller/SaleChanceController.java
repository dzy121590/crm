package com.dzy.crm.controller;

import com.dzy.crm.base.BaseController;
import com.dzy.crm.base.ResultInfo;
import com.dzy.crm.query.SaleChanceQuery;
import com.dzy.crm.service.SaleChanceService;
import com.dzy.crm.service.UserService;
import com.dzy.crm.utils.LoginUserUtil;
import com.dzy.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 董志远
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;

    /**
     * 多条件分页查询营销机会
     *
     * @param
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery sale) {

        return saleChanceService.querySaleChancesByParams(sale);
    }


    @RequestMapping("index")
    public String index() {

        return "saleChance/sale_chance";
    }

    /**
     * 添加操作
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveSaleChance(HttpServletRequest request, SaleChance saleChance) {

        saleChance.setCreateMan(userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(request)).getTrueName());
        saleChanceService.saveSaleChance(saleChance);

        return success("添加成功!");
    }

    /**
     * 更新操作
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance) {

        saleChanceService.updateSaleChance(saleChance);

        return success("更新成功!");
    }

    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id, Model model) {
        if (null != id) {
            model.addAttribute("saleChance", saleChanceService.selectByPrimaryKey(id));
        }
        return "saleChance/add_update";
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){

        saleChanceService.deleSaleChance(ids);
        return success("删除成功！");
    }

}
