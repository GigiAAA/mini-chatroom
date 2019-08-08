package com.bittech.Riri.chatroom.entity;

import java.util.Map;

/**
 * 后端发送给前端的信息实体
 */
public class Message2Client {
    //聊天内容
    private String content;
    //服务端登陆的所有用户列表
    private Map<String,String> names;

    public void setContent(String userName,String msg){
        this.content=userName+"说:"+msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "Message2Client{" +
                "content='" + content + '\'' +
                ", names=" + names +
                '}';
    }
}
