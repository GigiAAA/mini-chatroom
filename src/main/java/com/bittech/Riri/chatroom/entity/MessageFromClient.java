package com.bittech.Riri.chatroom.entity;

import lombok.Data;

/**
 * 前端发送给后端的信息实体类
 * 前端发给后端的消息格式:
 * 群聊:{"msg":"777","type":1}
 * 私聊:{"to":"0-","msg":"33333","type":2}
 */
@Data
public class MessageFromClient {
    //聊天信息
    private String msg;
    //聊天类别：1表示群聊 2表示私聊
    private String type;
    //私聊的对象SessionID
    private String to;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "MessageFromClient{" +
                "msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
