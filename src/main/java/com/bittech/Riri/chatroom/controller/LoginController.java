package com.bittech.Riri.chatroom.controller;

import com.bittech.Riri.chatroom.config.FreeMarkerListener;
import com.bittech.Riri.chatroom.service.AccountService;
import com.bittech.Riri.chatroom.utils.CommUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//登录
//配置Servlet是为了让浏览器发出的请求知道到达哪个servlet，也就是让tomcat
// 将封装好的request找到对应的servlet让其使用
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private AccountService accountService=new AccountService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取从前端传回的参数字段
        String userName=req.getParameter("username");
        String password=req.getParameter("password");
        resp.setContentType("text/html;charset=utf8");
        PrintWriter out=resp.getWriter();
        if(CommUtils.strIsNull(userName) || CommUtils.strIsNull(password)){
            //登录失败，停留登陆页面
            out.println("<script>\n" +
                    "    alert(\"用户名或密码为空\");\n" +
                    "    window.location.href=\"/index.html\";\n" +
                    "</script>");
        }
        if(accountService.userLogin(userName,password)){
            //登录成功，跳转到聊天页面
            //加载chat.ftl
            Template template=getTemplate(req,"chat.ftl");
            Map<String,String> map=new HashMap<>();
            map.put("username",userName);
            try {
                template.process(map,out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }else {
            //登录失败，停留在登陆页面
            out.println("<script>\n" +
                    "    alert(\"用户名或密码不正确！\");\n" +
                    "    window.location.href=\"/index.html\";\n" +
                    "</script>");
        }
    }

    private Template getTemplate(HttpServletRequest req,String fileName){
        Configuration cfg= (Configuration) req.getServletContext().getAttribute(FreeMarkerListener.TEMPLATE_KEY);
        try {
            return cfg.getTemplate(fileName);
        } catch (IOException e) {
            System.err.println("加载chat.ftl失败");
        }
        return null;
    }
}
