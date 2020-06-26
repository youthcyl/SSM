package com.Controller;

import com.Entity.Users;
import com.Service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class UserController {


    @RequestMapping("/Login")
    protected void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Users user = new Users();
        user.setUsername(request.getParameter("txtName"));
        user.setPassword(request.getParameter("txtPwd"));
        UsersService userService = new UsersService();
        if(userService.QueryUser(user))
        {
            Cookie cookieUserName = new Cookie("username", user.getUsername());//创建一个键值对的cookie对象
            cookieUserName.setMaxAge(60*60*24*30);//设置cookie的生命周期
            response.addCookie(cookieUserName);//添加到response中

            Cookie cookiePwd= new Cookie("password", user.getPassword());//创建一个键值对的cookie对象
            cookiePwd.setMaxAge(60*60*24*30);//设置cookie的生命周期
            response.addCookie(cookiePwd);//添加到response中

            HttpSession session = request.getSession(true);

            response.sendRedirect("page/index.html");

        }

        else{
            out.println("错误的用户名和密码");

        }

    }
}
