package com.bittech.Riri.chatroom.config;

import freemarker.template.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//表示具备监听的能力
@WebListener
public class FreeMarkerListener implements ServletContextListener {
    public static final String TEMPLATE_KEY="_template_";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //配置版本
        Configuration cfg=new Configuration(Configuration.VERSION_2_3_0);
        //配置加载ftl的路径
        try {
            cfg.setDirectoryForTemplateLoading(new File("D:\\maven_project\\mini-chatroom\\src\\main\\webapp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //配置页面编码
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        sce.getServletContext().setAttribute(TEMPLATE_KEY,cfg);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
