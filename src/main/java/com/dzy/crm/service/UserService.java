package com.dzy.crm.service;

import com.dzy.crm.base.BaseService;
import com.dzy.crm.dao.UserMapper;
import com.dzy.crm.model.UserModel;
import com.dzy.crm.utils.AssertUtil;
import com.dzy.crm.utils.Md5Util;
import com.dzy.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserMapper userMapper;


    public UserModel login(String userName, String passWord) {
        /*
    1.判断用户名和密码是否为空
    2.根据用户名查找用户是否存在
    3.查看密码是否正确
    4.登陆成功
     */
        checkLoginParams(userName, passWord);
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(null == user, "用户名不存在！");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(passWord))), "密码不正确，请重新输入！");
        return buildUserInfo(user);
    }

    private UserModel buildUserInfo(User user) {

        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;

    }

    private void checkLoginParams(String userName, String passWord) {

        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(passWord), "密码不能为空！");

    }
}
