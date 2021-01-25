package com.igeek.shop.dao;

import com.igeek.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @version 1.0
 * @Description TODO
 * @Author chenmin
 * @Date 2021/1/22 11:13
 */
public class BasicDao<T> {

    QueryRunner runner = new QueryRunner();

    //增删改
    public int update(String sql , Object...params) throws SQLException {
        return runner.update(DataSourceUtils.getConnection(),sql,params);
    }
    //查询单条信息
    public T getBean(String sql,Class clazz,Object...params) throws SQLException {
        return (T) runner.query(DataSourceUtils.getConnection(),sql,new BeanHandler<>(clazz),params);
    }
    //
    public List<T> getBeanList(String sql, Class clazz, Object...param) throws SQLException {
        return (List<T>) runner.query(DataSourceUtils.getConnection(),sql,new BeanListHandler<>(clazz),param);

    }

    //查询单个值
    public Object getSingleValue(String sql,Object...params) throws SQLException {
        return  runner.query(DataSourceUtils.getConnection(), sql, new ScalarHandler<>(), params);
    }

}
