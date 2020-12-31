package com.dzy.crm.controller;

import com.dzy.crm.base.ResultInfo;
import com.dzy.crm.exceptions.ParamsException;
import com.dzy.crm.model.UserModel;
import com.dzy.crm.service.UserService;
import com.dzy.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 董志远
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userName
     * @param passWord
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public ResultInfo login(String userName, String passWord) {

        ResultInfo resultInfo = new ResultInfo();

        UserModel userModel = userService.login(userName, passWord);

        resultInfo.setResult(userModel);

        return resultInfo;
    }


    @PostMapping("/user/updatePwd")
    @ResponseBody
    public ResultInfo updatePwd(HttpServletRequest request, String oldPassWord, String newPassWord, String confirmPassWord) {
        ResultInfo resultInfo = new ResultInfo();

        userService.updateUserPassWord(LoginUserUtil.releaseUserIdFromCookie(request), oldPassWord, newPassWord, confirmPassWord);

        return resultInfo;

    }

    @RequestMapping("user/toPasswordPage")
    public String toPasswordPage() {

        return "user/password";
    }

    @ResponseBody
    @RequestMapping("user/queryAllSales")
    public List<Map<String,Object>> queryAllSales(){
        return userService.queryAllSales();
    }

}
