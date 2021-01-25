package com.igeek.shop.service;

import com.igeek.shop.dao.UserDao;
import com.igeek.shop.entity.User;
import com.igeek.shop.utils.DataSourceUtils;

import java.sql.SQLException;

/**
 * @version 1.0
 * @Description 用户模块的业务逻辑类
 * @Author chenmin
 * @Date 2021/1/22 11:19
 */
public class UserService {

    private UserDao dao = new UserDao();

    //注册
    public boolean regist(User user){
        try {
            return dao.insert(user)>0 ? true :false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //登录
    public boolean login(String username,String password){
        User user = null;
        try {
            user = dao.selectOne(username);
            System.out.println(user);
            if(user!=null){
                if(user.getPassword()!=null && !password.equals("")){
                    if(user.getPassword().equals(password)){
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                DataSourceUtils.closeConnection();
                DataSourceUtils.closeResultSet(null);
                DataSourceUtils.closeStatement(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    //激活
    public boolean active(String code){
        try {
            int i = dao.updateState(code);
            if(i>0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //校验昵称是否存在
    public boolean validate(String username){
        try {
            Long count = dao.selectOneByusername(username);
            if(count>0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
