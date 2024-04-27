package com.example.websocketdemo.intecepter;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 拦截器要在端点中配置，才可以使用
 */
public class HttpHandShakeIntecepter implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("【握手拦截器】beforeHandshake");
        if(request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletServerHttpRequest=(ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest=servletServerHttpRequest.getServletRequest();
            String sessionId=httpServletRequest.getSession().getId();
            attributes.put("sessionId",sessionId);
            System.out.println("【sessionid】--"+sessionId);

            //跨域情况下手动设置cookie，否则每次请求都会创建新的会话，也就是sessionId会不一样
            ServletServerHttpResponse httpResponse=(ServletServerHttpResponse) response;
            //保存到客户端磁盘
            Cookie cookie = new Cookie("JSESSIONID",sessionId);
            cookie.setDomain("127.0.0.1");
            cookie.setPath("/");
            httpResponse.getServletResponse().addCookie(cookie);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("【握手拦截器】afterHandshake");
        if(request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest httpServletRequest=(ServletServerHttpRequest) request;
            String sessionId=httpServletRequest.getServletRequest().getSession().getId();
            System.out.println("【sessionid】--"+sessionId);
        }
    }
}
