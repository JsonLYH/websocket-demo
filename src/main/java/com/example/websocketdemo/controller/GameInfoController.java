package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.InMessage;
import com.example.websocketdemo.model.OutMessage;
import com.example.websocketdemo.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameInfoController {
    @Autowired
    WebsocketService websocketService;
    /**
     * MessageMapping--前端发送消息到服务端的路径
     * SendTo--服务端发送消息到前端(前端所要订阅的地址)
     * @param message
     * @return
     */
    @MessageMapping("/v1/chat")
    @SendTo("/topic/game_chat")
    public OutMessage gameInfo(InMessage message){
        return new OutMessage(message.getContent());
    }

    /**
     * clientID模拟多人在同一个房间进行聊天或一对一
     * @param clientId
     * @param message
     * @return
     */
    @MessageMapping("/v2/chat/{clientId}")
    @SendTo("/topic/reply/{clientId}")
    public OutMessage gameInfo(@DestinationVariable String clientId, InMessage message){
        return new OutMessage(message.getContent());
    }

    /**
     * 使用SimpMessagingTemplate的方式发送消息
     * 比@SendTo更加的灵活
     * @param clientId
     * @param message
     */
    @MessageMapping("/v3/chat/{clientId}")
    public void gameInfoV3(@DestinationVariable String clientId, InMessage message){
        websocketService.sendGameInfo("/topic/v3/reply/"+clientId,message);
    }
}
