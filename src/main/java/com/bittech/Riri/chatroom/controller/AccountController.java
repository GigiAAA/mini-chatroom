package com.bittech.Riri.chatroom.controller;

import com.bittech.Riri.chatroom.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//配置访问后端类
//注册
@WebServlet(urlPatterns = "/doRegister")
public class AccountController extends HttpServlet {
    private AccountService accountService=new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName=req.getParameter("username");
        String password=req.getParameter("password");
        resp.setContentType("text/html;charset=utf8");
        PrintWriter writer=resp.getWriter();
        if(accountService.userRegister(userName,password)){
            //用户注册成功-->弹框提示-->返回登陆界面
            writer.println("<script>\n" +
                    "    alert(\"注册成功\");\n" +
                    "    window.location.href=\"/index.html\";\n" +
                    "</script>");
        }else {
            //用户注册失败-->弹框提示-->返回注册界面(保留原页面)
            writer.println("<script>\n" +
                    "    alert(\"注册失败\");\n" +
                    "    window.location.href=\"/registration.html\";\n" +
                    "</script>");
        }
    }
}
