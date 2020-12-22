package com.dzy.crm.controller;

import com.dzy.crm.base.ResultInfo;
import com.dzy.crm.exceptions.ParamsException;
import com.dzy.crm.model.UserModel;
import com.dzy.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userName
     * @param passWord
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public ResultInfo login(String userName, String passWord) {

        ResultInfo resultInfo = new ResultInfo();
        try {
            UserModel userModel = userService.login(userName, passWord);
            /*
            1.用户信息放在session中
            2.用户信息放在cookie中
             */
            resultInfo.setResult(userModel);

        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(e.getCode());
            resultInfo.setMsg(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(500);
            resultInfo.setMsg("操作失败！");
        }
        return resultInfo;
    }


}
