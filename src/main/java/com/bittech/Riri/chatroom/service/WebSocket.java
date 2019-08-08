package com.bittech.Riri.chatroom.service;

import com.bittech.Riri.chatroom.entity.Message2Client;
import com.bittech.Riri.chatroom.entity.MessageFromClient;
import com.bittech.Riri.chatroom.utils.CommUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket")
public class WebSocket {
    //存储所有连接到后端的websocket
    private static CopyOnWriteArraySet<WebSocket> clients=new CopyOnWriteArraySet<>();
    //缓存所有的用户列表
    private static Map<String,String> names=new ConcurrentHashMap<>();
    //绑定当前websocket会话
    private Session session;
    //当前客户端的用户名
    private String userName;
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        //username='+'${username}
        String userName=session.getQueryString().split("=")[1];
        this.userName=userName;
        //将客户端聊天实体保存到clients
        clients.add(this);
        //将当前用户以及SessionID保存到用户列表
        names.put(session.getId(),userName);
        System.out.println("有新的连接，ID为:"+session.getId()+",用户名为:"+userName);
        //发送给所有在线用户一个上线通知
        Message2Client message2Client=new Message2Client();
        message2Client.setContent(userName+"上线啦!");
        message2Client.setNames(names);
        //发送信息
        String jsonStr=CommUtils.Object2Json(message2Client);
        //新用户上线时，向所有在线用户发送通知
        for(WebSocket webSocket:clients){
            webSocket.sendMsg(jsonStr);
        }
    }
    @OnError
    public void onError(Throwable e){
        System.err.println("webSocket连接失败");
    }
    @OnMessage
    public void onMessage(String msg){
        //将msg反序列化为MessageFromClient
        MessageFromClient messageFromClient= (MessageFromClient) CommUtils.json2Object(msg,MessageFromClient.class);
        if(messageFromClient.getType().equals("1")){
            //群聊信息
            String content=messageFromClient.getMsg();
            Message2Client message2Client=new Message2Client();
            message2Client.setContent(content);
            message2Client.setNames(names);
            //广播发送
            for(WebSocket webSocket:clients){
                webSocket.sendMsg(CommUtils.Object2Json(message2Client));
            }
        }else if(messageFromClient.getType().equals("2")){
            //私聊信息
            //私聊信息内容
            String content=messageFromClient.getMsg();
            int toL=messageFromClient.getTo().length();
            String tos[]=messageFromClient.getTo().substring(0,toL-1).split("-");
            List<String> lists=Arrays.asList(tos);
            //给指定的SessionID发送信息
            for (WebSocket webSocket:clients){
                if(lists.contains(webSocket.session.getId()) && this.session.getId()!=webSocket.session.getId()){
                    //发送私聊信息
                    Message2Client message2Client=new Message2Client();
                    message2Client.setContent(userName,content);
                    message2Client.setNames(names);
                    webSocket.sendMsg(CommUtils.Object2Json(message2Client));
                }
            }
        }
    }
    //用户下线
    @OnClose
    public void onClose(){
        //将客户端聊天实体移除
        clients.remove(this);
        names.remove(session.getId());
        System.out.println("有连接下线，ID为:"+session.getId()+",用户名为:"+userName);
        //发送给所有在线用户一个下线通知
        Message2Client message2Client=new Message2Client();
        message2Client.setContent(userName+"下线了!");
        message2Client.setNames(names);
        //发送信息
        String jsonStr=CommUtils.Object2Json(message2Client);
        //新用户上线时，向所有在线用户发送通知
        for(WebSocket webSocket:clients){
            webSocket.sendMsg(jsonStr);
        }
    }
    public void sendMsg(String msg){
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
