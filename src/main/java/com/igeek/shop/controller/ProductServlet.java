package com.igeek.shop.controller;

import com.igeek.shop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProductServlet")
public class ProductServlet extends HttpServlet {
    private ProductService service=new ProductService();
    //展示首页
    public void index(){

    }
}
