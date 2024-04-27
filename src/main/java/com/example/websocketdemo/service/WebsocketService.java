package com.example.websocketdemo.service;

import com.example.websocketdemo.model.InMessage;
import com.example.websocketdemo.model.OutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * simpMessagingTemplate的方式会更灵活
     * @param dest
     * @param inMessage
     */
    public void sendGameInfo(String dest, InMessage inMessage){
        simpMessagingTemplate.convertAndSend(dest,new OutMessage(inMessage.getContent()));
    }
}
