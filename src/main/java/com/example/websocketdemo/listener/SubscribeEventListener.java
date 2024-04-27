package com.example.websocketdemo.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * 订阅监听器
 */
@Component
public class SubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {
    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor stompHeaderAccessor=StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("【订阅监听器事件类型】"+stompHeaderAccessor.getCommand().getMessageType());
        //拦截器设置session后，这里是可以拿到session信息的
        System.out.println("【监听器获取sessionid】---"+stompHeaderAccessor.getSessionAttributes().get("sessionId"));
    }
}
