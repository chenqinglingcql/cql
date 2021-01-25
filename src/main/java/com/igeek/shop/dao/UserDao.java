package com.igeek.shop.dao;

import com.igeek.shop.entity.User;

import java.sql.SQLException;

/**
 * @version 1.0
 * @Description 用户模块的数据交互类
 * @Author chenmin
 * @Date 2021/1/22 11:15
 */
public class UserDao extends BasicDao<User> {

    //插入用户数据
    public int insert(User user) throws SQLException {
        String sql = "insert into user values(?,?,?,?,?,?,?,?,0,?,?)";
        int i = this.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
                user.getName(), user.getEmail(), user.getTelephone(),
                user.getBirthday(), user.getSex(), user.getCode(), user.getAddress());
        return i;
    }

    //查询单条数据
    public User selectOne(String username) throws SQLException {
        String sql="select * from user where username=?";
        User user = this.getBean(sql, User.class, username);
        return user;
    }

    //更新状态
    public int updateState(String code) throws SQLException {
        String sql="update user set state=? where code=? ";
        int i = this.update(sql, 1, code);
        return i;
    }
    //通过用户昵称查询信息
    public Long selectOneByusername(String username) throws SQLException {
        String sql="select * count(*) from users where username=?";
        Long rs = (Long)this.getSingleValue(sql, username);
        return  rs;
    }

}
