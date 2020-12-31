package com.dzy.crm.service;

import com.dzy.crm.base.BaseService;
import com.dzy.crm.dao.UserMapper;
import com.dzy.crm.model.UserModel;
import com.dzy.crm.utils.AssertUtil;
import com.dzy.crm.utils.Md5Util;
import com.dzy.crm.utils.UserIDBase64;
import com.dzy.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 董志远
 */
@Service
public class UserService extends BaseService<User, Integer> {

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
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;

    }

    private void checkLoginParams(String userName, String passWord) {

        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(passWord), "密码不能为空！");

    }

    public void updateUserPassWord(Integer userId, String oldPassWord, String newPassWord, String confirmPassWord) {

        /**
         * 1.参数校验
         *      userId 用户id 非空
         *      oldPassWord 旧密码 与数据库密文保持一致 非空
         *      newPassWord 新密码非空 不能和oldPassWord一样
         *      confirmPassWord 确认密码 非空
         */
        checkParams(userId, oldPassWord, newPassWord, confirmPassWord);
        User user=userMapper.selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassWord));
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"密码更新失败！");
    }

    private void checkParams(Integer userId, String oldPassWord, String newPassWord, String confirmPassWord) {
        //根据id查找用户
        User temp = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(null == userId || null == temp, "用户不存在");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassWord), "请输入密码！");
        AssertUtil.isTrue(StringUtils.isBlank(newPassWord), "请输入新密码！");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassWord), "请再输入密码！");
        AssertUtil.isTrue(!(temp.getUserPwd().equals(Md5Util.encode(oldPassWord))),"密码不正确，请重新输入！");
        AssertUtil.isTrue(!(newPassWord.equals(confirmPassWord)),"两次输入的密码不一致");
        AssertUtil.isTrue((oldPassWord.equals(newPassWord)),"新密码不能与旧密码相同");
    }

    public List<Map<String,Object>> queryAllSales(){
        return userMapper.queryAllSales();
    }

}
