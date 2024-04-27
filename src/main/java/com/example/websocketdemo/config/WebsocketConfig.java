package com.example.websocketdemo.config;

import com.example.websocketdemo.intecepter.HttpHandShakeIntecepter;
import com.example.websocketdemo.intecepter.SocketChannelIntecepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 首次连接初始化： 握手拦截器-》channel拦截器-》连接监听器-》订阅监听器
 * 初始化过后，后续发送消息，只走channel拦截器
 * 或者后期连接断开、取消订阅、订阅等情况就会走连接监听器或订阅监听器
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 基站端点(进行任何通信的前提是先链接到基站)
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 无法在postman进行链接
         * 前端使用socketjs链接的话，要使用http、https协议进行链接，否则会报错
         * 本地测试链接跨域问题，后端报错，使用setAllowedOriginPatterns替换为setAllowedOrigins即可
         * web端端点
         */
        registry.addEndpoint("/endpoint-websocket").addInterceptors(new HttpHandShakeIntecepter()).setAllowedOriginPatterns("*").withSockJS();
        /**
         * uniapp开发app、小程序所用端点
         * 可以在postman、uniapp开发app或小程序时进行链接
         */
        registry.addEndpoint("/endpoint-postman-websocket").setAllowedOriginPatterns("*").setAllowedOrigins("*");
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //订阅信息的路径前缀
        config.enableSimpleBroker("/topic");
        //发送信息的路径前缀
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors( new SocketChannelIntecepter());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors( new SocketChannelIntecepter());
    }

}
