package com.igeek.shop.dao;

import com.igeek.shop.entity.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description TODO
 * @Author Lemon
 * @Date 2021/1/25 14:26
 */
public class ProductDao extends BasicDao<Product> {
    //热门商品查询
    public List<Product> selectAllByIsHot() throws SQLException {
        String sql="select* from product where ishot=? limit 9";
        List<Product> list = this.getBeanList(sql, Product.class, 1);
        return list;
    }
    //最新商品查询
    public List<Product> selectAllByIsFreash() throws SQLException {
        String sql="select * from product order by pdate limit 9";
        List<Product> list = this.getBeanList(sql, Product.class);
        return list;
    }
}
