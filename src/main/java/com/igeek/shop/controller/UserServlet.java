package com.igeek.shop.controller;

import com.igeek.shop.entity.User;
import com.igeek.shop.service.UserService;
import com.igeek.shop.utils.CommonUtils;
import com.igeek.shop.utils.MD5Utils;
import com.igeek.shop.utils.MailUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "UserServlet" , urlPatterns = "/user")
public class UserServlet extends BasicServlet {

    private UserService service = new UserService();

    //用户注册
    public void regist(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        User user = new User();
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class clazz, Object o) {
                Date birthday = null;
                if(o instanceof String){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        birthday = sdf.parse(request.getParameter("birthday"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return birthday;
            }
        }, Date.class);

        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        user.setUid(CommonUtils.getUUID().replaceAll("-",""));
        String code = CommonUtils.getUUID().replaceAll("-","");
        user.setCode(code);
        //通过MD5对密码进行加密存储
        user.setPassword(MD5Utils.md5(user.getPassword()));

        boolean flag = service.regist(user);
        if(flag){
            //注册成功
            //发送邮件，激活账户
            String emailMsg="这是一份激活邮件，请点击<a href='http://localhost:8080/user?method=active&code="+code+"'>"+code+"</a>激活";
            try {
                MailUtils.sendMail(user.getEmail(),"注册邮箱激活",emailMsg);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("registSuccess.jsp").forward(request,response);
        }else{
            //注册失败
            request.getRequestDispatcher("registFail.jsp").forward(request,response);
        }
    }

    //用户激活
    public void active(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        boolean flag = service.active(code);
        if(flag){
            //激活成功
            response.sendRedirect("login.jsp");
        }else{
            response.sendRedirect("error.jsp");
        }
    }

    //用户登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
       //MD5进行加密
        password = MD5Utils.md5(password);
        boolean flag = service.login(username, password);
        if(flag){
            //登录成功
            request.getRequestDispatcher("order_list.jsp").forward(request,response);
        }else{
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }

    //用户校验
    public void validate(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        boolean flag = service.validate(username);
        //封装成json数据格式，响应至客户端  json格式：'flag":flag
        String str="{\"flag\":"+flag+"}";
        response.getWriter().write(str);
    }


}
