package com.example.websocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings( CorsRegistry registry) {
        //单纯想解决websocket跨域的话，这里可以不设置
        //这里是配置http请求跨域的
        registry.addMapping("/**")//允许请求路径
                //.allowedOrigins("*")//表示允许所有网址发起跨域请求
                .allowedOriginPatterns("*")//表示允许所有网址发起跨域请求
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")//表示允许跨域请求的方法
                .allowedMethods("*")
                .maxAge(3600)//表示在3600秒内不需要再发送预校验请求
                .allowCredentials(true);//允许客户端携带验证信息，即允许携带cookie
    }
}
